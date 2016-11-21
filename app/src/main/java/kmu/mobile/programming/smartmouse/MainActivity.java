package kmu.mobile.programming.smartmouse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import kmu.mobile.programming.smartmouse.comunication.Communication;
import kmu.mobile.programming.smartmouse.comunication.bluetooth.BluetoothCommunication;
import kmu.mobile.programming.smartmouse.comunication.bluetooth.BluetoothConnection;

public class MainActivity extends AppCompatActivity {
  public static int BLUETOOTH_ON = 0001;
  private BluetoothConnection mBluetoothConnect;
  private BluetoothCommunication mBluetoothCommunication;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission();
    mBluetoothCommunication = new BluetoothCommunication();
    mBluetoothConnect = BluetoothConnection.getInstance(this, mBluetoothCommunication);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    mBluetoothConnect.resultBluetoothOn(requestCode, resultCode);
  }

  private void getPermission() {
    int permission = checkSelfPermission(Manifest.permission.BLUETOOTH);
    if(permission == PackageManager.PERMISSION_DENIED) {
      requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 1);
    }
  }

  public void onClick(View view) {
    if (view.getId() == R.id.CONNECT) {
      mBluetoothConnect.selectBluetooth();
    }
  }
}
