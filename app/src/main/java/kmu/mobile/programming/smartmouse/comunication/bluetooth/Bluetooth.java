package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import kmu.mobile.programming.smartmouse.MainActivity;

/**
 * Created by acoustically on 16. 11. 18.
 */
public class Bluetooth {
  private static Bluetooth instance;
  private BluetoothManager mManager;
  private BluetoothAdapter mAdapter;
  private MainActivity mActivity;
  public boolean isOn = false;

  private Bluetooth(MainActivity mainActivity) {
    mActivity = mainActivity;
    mManager = (BluetoothManager)mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
    mAdapter = getAdapter();
    bluetoothOn();
  }

  public static Bluetooth getInstance(MainActivity activity) {
    if (instance == null) {
      instance = new Bluetooth(activity);
    }
    return instance;
  }

  private BluetoothAdapter getAdapter() {
    return mManager.getAdapter();
  }

  private void bluetoothOn() {
    if(mAdapter.isEnabled()) {
      Log.d("MYLOG", "bluetooth inEnble");
      return;
    } else {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      mActivity.startActivityForResult(intent, MainActivity.BLUETOOTH_ON);
    }
  }
}
