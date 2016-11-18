package kmu.mobile.programming.smartmouse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import kmu.mobile.programming.smartmouse.comunication.bluetooth.Bluetooth;

public class MainActivity extends AppCompatActivity {
  public static int BLUETOOTH_ON = 0001;
  private Bluetooth mBluetooth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getPermission();
    mBluetooth = Bluetooth.getInstance(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == BLUETOOTH_ON) {
      if (resultCode == RESULT_OK) {
        Log.d("MYLOG", "bluetooth on");
        mBluetooth.isOn = true;
      } else {
        Log.d("MYLOG", "bluetooth off");
      }
    }
  }

  private void getPermission() {
    int permission = checkSelfPermission(Manifest.permission.BLUETOOTH);
    if(permission == PackageManager.PERMISSION_DENIED) {
      requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 1);
    }
  }
}
