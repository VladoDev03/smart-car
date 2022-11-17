package com.example.remote

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.remote.databinding.ActivityMainBinding
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter : BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "This device does not support bluetooth", Toast.LENGTH_SHORT).show()
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 1)
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBtIntent)
        }

        val device: BluetoothDevice? = bluetoothAdapter?.bondedDevices?.firstOrNull {
            it.name == "HC-06"
        }

        if (device == null) {
            Toast.makeText(this, "There is no HC-06 nearby :(", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, device.name, Toast.LENGTH_SHORT).show()
        val socket = device.createRfcommSocketToServiceRecord(device.uuids[0].uuid)
        socket.connect()
        val outStream = socket.outputStream

        binding.forward.setOnClickListener {
            sendData("forward", outStream)
        }

        binding.backwards.setOnClickListener {
            sendData("backwards", outStream)
        }

        binding.left.setOnClickListener {
            sendData("left", outStream)
        }

        binding.right.setOnClickListener {
            sendData("right", outStream)
        }

        binding.stop.setOnClickListener {
            sendData("stop", outStream)
        }
    }

    fun sendData(direction: String, outStream: OutputStream) {
        outStream.write(direction.toByteArray())
    }
}