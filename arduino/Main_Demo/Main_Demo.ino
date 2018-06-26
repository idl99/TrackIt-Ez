// Library for ESP8266 WiFi Module
#include <ESP8266WiFi.h>
// WiFi Credentials
const char* ssid     = "IDL_WIFI";
const char* password = "AHJ1GLELN2J";

// Serial Peripheral Interface
#include <SPI.h>

// LIbrary for RFID-NFC Module
#include <MFRC522.h>
// Pin configuration for RFID Module
#define SS_PIN D4
#define RST_PIN D3
// Create an instance of RFID
MFRC522 mfrc522(SS_PIN, RST_PIN);

// Firebase Libraries
#include <Firebase.h>
#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>

// JSON Library - FirebaseArduino dependency
#include <ArduinoJson.h>

// Firebase Credentials
#define FIREBASE_HOST "trackit-ez.firebaseio.com"
#define FIREBASE_SECRET "IPglLeYsAg1MIYFTKRfwCTIqk6atz9pdmEsgMhA7"

void setup() {
  Serial.begin(115200);

  // Connecting to WiFi AP
  Serial.print("Connecting to ");
  Serial.print(ssid);

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\n");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  Serial.println();

  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522

  Firebase.begin(FIREBASE_HOST, FIREBASE_SECRET); // Initiate Firebase

}

void loop() {

  if ( ! mfrc522.PICC_IsNewCardPresent())
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial())
  {
    return;
  }

  String readUid = readRfid();
  Serial.println("UID is " + readUid);

  // Tag related logic goes here
  if (isTagExists(readUid)) {
    boolean isInUse = getPreviousInUse(readUid);
    if (isInUse) {
      Firebase.setBool("userId1/nfcTags/" + readUid + "/inUse", false);
      Serial.println("Existing tag " + readUid + " in use, updated in Firebase");
    } else {
      Firebase.setBool("userId1/nfcTags/" + readUid + "/inUse", true);
    }
  } else if (!isTagExists(readUid)) {
    Firebase.setBool("userId1/nfcTags/" + readUid + "/inUse", true);
  }

  Serial.println();

  delay(2500);

}

// Method to read NFC Tag UID
// returns the NFC UID
String readRfid() {
  String content = "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++)
  {
    if (i != 0) {
      content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
    }
    content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  content.toUpperCase();
  return content;
}

// Method that checks if NFC Tag registered in database
boolean isTagExists(String uid) {
  FirebaseObject firebaseObj = Firebase.get("userId1/nfcTags/" + uid + "");
  JsonVariant firebaseJson = firebaseObj.getJsonVariant("");
  if ((firebaseJson.as<String>()).equals("null")) {
    Serial.println("Tag doesn't exist");
    return false;
  } else {
    Serial.println("Tag exists");
    return true;
  }
}

boolean getPreviousInUse(String uid) {
  return Firebase.getBool("userId1/nfcTags/" + uid + "/inUse");
}

