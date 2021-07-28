# BLETest

This is an extremely minimal Android app that is capable of reproducing the wcnss crash on a Quectel SC600Y in Android 9.

To reproduce the problem:
1.  Make sure bluetooth is enabled on the device.
2.  Run the app.  
3.  Go into the Android Settings app, Apps & Notifications -> App Permissions -> Location and enable Location permission for BLETest.
4.  The app will begin BLE advertising with a Device Name of Sensera_BLETest.
5.  On a different BLE enabled device like a smartphone, use a BLE application to connect with Sensera_BLETest.

This will SOMETIMES cause the SC600Y to experience a kernel panic and crash.

Sometimes it crashes, sometimes it doesn't.  I do not know why it only crashes sometimes.
