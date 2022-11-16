#include <Wire.h>

String cmd;

int m11 = 2;
int m12 = 3;
int m21 = 4;
int m22 = 5;

void setup() {
  Serial.begin(9600);
  
  pinMode(m11, OUTPUT);
  pinMode(m12, OUTPUT);
  pinMode(m21, OUTPUT);
  pinMode(m22, OUTPUT);

  forward();
  delay(3000);
  backwards();
  delay(3000);
  left();
  delay(3000);
  right();
  delay(3000);
  freeze();
}

void loop() {
  if (Serial.available() > 0) {
    cmd = Serial.readStringUntil('\r')

    switch(cmd) {
      case "f":
        forward();
        break;
      case "b":
        backwards();
        break;
      case "l":
        left();
        break;
      case "r":
        right();
        break;
      case "s":
        freeze();
        break;
    }
  }
}

void forward() {
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  digitalWrite(m21, HIGH);
  digitalWrite(m22, LOW);
}

void backwards() {
  digitalWrite(m11, LOW);
  digitalWrite(m12, HIGH);
  digitalWrite(m21, LOW);
  digitalWrite(m22, HIGH);
}

void left() {
  digitalWrite(m11, LOW);
  digitalWrite(m12, HIGH);
  digitalWrite(m21, HIGH);
  digitalWrite(m22, LOW);
}

void right() {
  digitalWrite(m11, HIGH);
  digitalWrite(m12, LOW);
  digitalWrite(m21, LOW);
  digitalWrite(m22, HIGH);
}

void freeze() {
  digitalWrite(m11, LOW);
  digitalWrite(m12, LOW);
  digitalWrite(m21, LOW);
  digitalWrite(m22, LOW);
}
