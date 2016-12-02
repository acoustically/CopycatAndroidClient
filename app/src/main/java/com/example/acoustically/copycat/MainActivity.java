package com.example.acoustically.copycat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.acoustically.copycat.bluetooth.BluetoothConnection;
import com.example.acoustically.copycat.bluetooth.SocketConnect;
import com.example.acoustically.copycat.bluetooth.SocketIOStream;

public class MainActivity extends AppCompatActivity {
  public static int BLUETOOTH_ON = 0001;
  private BluetoothConnection mBluetoothConnect;
  private SocketConnect mBluetoothCommunication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission(Manifest.permission.ACCESS_FINE_LOCATION)
      .getPermission(Manifest.permission.BLUETOOTH)
      .getPermission(Manifest.permission.BLUETOOTH_ADMIN)
      .getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    mBluetoothCommunication = new SocketConnect("SocketConnect");
    mBluetoothConnect = new BluetoothConnection(this, mBluetoothCommunication);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    mBluetoothConnect.resultBluetoothOn(requestCode, resultCode);
  }

  private MainActivity getPermission(String permission) {
    int currentPermission = checkSelfPermission(permission);
    if (currentPermission == PackageManager.PERMISSION_DENIED) {
      requestPermissions(new String[]{permission}, 1);
    }
    return this;
  }

  public void onClick(View view) {
    if (view.getId() == R.id.CONNECT) {
      mBluetoothConnect.selectBluetooth();
    }
  }

  @Override
  protected void onDestroy() {
    try {
      SocketIOStream.getInstance().CloseSocket();
    } catch (Exception e) {
      Log.e("MYLOG", "Bluetooth not close");
    }
    mBluetoothConnect.unRegistReceiver();
    super.onDestroy();
  }
}
