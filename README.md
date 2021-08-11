# BLETest

This is an extremely minimal Android app that is capable of reproducing the wcnss crash on a Quectel SC600Y in Android 9.

To reproduce the problem:
0.  Install this app on a Quectel SC600Y device.
1.  Make sure bluetooth is enabled on the device.
2.  Run the app.  
3.  Go into the Android Settings app, Apps & Notifications -> App Permissions -> Location and enable Location permission for BLETest.
4.  The app will begin BLE advertising with a Device Name of Sensera_BLETest.
5.  On a different BLE enabled device like a smartphone, use a BLE application to connect with Sensera_BLETest.

This will SOMETIMES cause the SC600Y to experience a kernel panic and crash.

Sometimes it crashes, sometimes it doesn't.  I do not know why it only crashes sometimes.
See the wcnsslog.txt file for a serial crash log from the SC600Y.


Addition 7/28/2021:   It has been suggested that this crash happens when BLE is accessed without "Location" enabled.  I don't know precisely what that means, but I have tried it many times with Settings -> Location set to various states, and with the app-specific "ACCESS_FINE_LOCATION" declared in the manifest and granted via user dialog or from within the settings menu as described in step 3 above.  I am convinced by LOTS of testing that neither of these settings have much to do with whether the wcnss crash occurs or not.
