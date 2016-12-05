package com.example.acoustically.copycat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.acoustically.copycat.bluetooth.BluetoothConnection;
import com.example.acoustically.copycat.bluetooth.socket.connection.SocketConnection;
import com.example.acoustically.copycat.bluetooth.socket.connection.SocketIOStream;

public class MainActivity extends AppCompatActivity {
  public static int BLUETOOTH_ON = 0001;
  private BluetoothConnection mBluetoothConnect;
  private SocketConnection mSocketConnection;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission(Manifest.permission.ACCESS_FINE_LOCATION)
      .getPermission(Manifest.permission.BLUETOOTH)
      .getPermission(Manifest.permission.BLUETOOTH_ADMIN)
      .getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    mSocketConnection = new SocketConnection("SocketConnect");
    mBluetoothConnect = new BluetoothConnection(this, mSocketConnection);
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
