package com.sensera.bletest;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Test application that
 */
public class BLETestApp extends Application implements Handler.Callback {
    private static final String TAG = "BLETestApp";
    private static final int MSG_CHECK_LOCATION_PERMISSION = 1;

    private Handler handler;
    private BluetoothLeAdvertiser advertiser;

    private final AdvertiseCallback advCallback = new AdvertiseCallback() {
        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.i(TAG, "Not advertising: " + errorCode);
        }

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.i(TAG, "BLE advertising started");
        }
    };

    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "######## Launching BLETEST ##############");
        handler = new Handler(this);
        handler.sendEmptyMessageDelayed(MSG_CHECK_LOCATION_PERMISSION, 1000);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.what == MSG_CHECK_LOCATION_PERMISSION) {
            Log.i(TAG, "Checking for location permission and BT enable (turn them on in Settings)");

            if (!checkLocationPermission()) {
                handler.sendEmptyMessageDelayed(MSG_CHECK_LOCATION_PERMISSION, 1000);
                return true;
            }

            if (!isBluetoothEnabled()) {
                handler.sendEmptyMessageDelayed(MSG_CHECK_LOCATION_PERMISSION, 1000);
                return true;
            }

            startAdvertising();

            return true;
        }
        return false;
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Do not have location permission yet");
            return false;
        } else {
            return true;
        }
    }

    private boolean isBluetoothEnabled() {
        BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Log.i(TAG, "Bluetooth is not enabled.   Turn it on in Settings please.");
            return false;
        } else {
            return true;
        }
    }

    private boolean startAdvertising() {
        BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothAdapter.setName("Sensera_BLETest");
        advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

        AdvertiseSettings advSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM).setConnectable(true).build();

        AdvertiseData advData = new AdvertiseData.Builder().setIncludeTxPowerLevel(false).build();

        AdvertiseData advScanResponse = new AdvertiseData.Builder().setIncludeDeviceName(true)
                .build();

        advertiser.startAdvertising(advSettings, advData, advScanResponse, advCallback);

        return true;
    }
}
