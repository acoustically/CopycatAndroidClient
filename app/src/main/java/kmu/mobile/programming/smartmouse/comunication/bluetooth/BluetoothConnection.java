package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import kmu.mobile.programming.smartmouse.MainActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by acoustically on 16. 11. 18.
 */
public class BluetoothConnection {
  private static BluetoothConnection instance;
  private BluetoothManager mManager;
  private BluetoothAdapter mAdapter;
  private MainActivity mActivity;
  private boolean isOn = false;

  public static BluetoothConnection getInstance(MainActivity activity) {
    if (instance == null) {
      instance = new BluetoothConnection(activity);
    }
    return instance;
  }
  public void resultBluetoothOn(int requestCode, int resultCode) {
    if (requestCode == MainActivity.BLUETOOTH_ON) {
      if (resultCode == MainActivity.RESULT_OK) {
        Log.d("MYLOG", "bluetooth on");
      } else {
        Log.d("MYLOG", "bluetooth off");
      }
    }
  }

  public void selectBluetooth() {
    bluetoothOn();
    final CharSequence[] bluetoothList = getBluetoothList();
    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
    builder.setTitle("블루투스 선택");
    builder.setItems(bluetoothList, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        connectBluetooth(bluetoothList[i].toString());
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private BluetoothConnection(MainActivity mainActivity) {
    mActivity = mainActivity;
    mManager = (BluetoothManager)mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
    mAdapter = getAdapter();
  }

  private BluetoothAdapter getAdapter() {
    return mManager.getAdapter();
  }

  private void bluetoothOn() {
    if(mAdapter.isEnabled()) {
      Log.d("MYLOG", "bluetooth inEnble");
    } else {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      mActivity.startActivityForResult(intent, MainActivity.BLUETOOTH_ON);
    }
  }

  private CharSequence[] getBluetoothList() {
    Set<BluetoothDevice> devices = mAdapter.getBondedDevices();
    List<String> bluetoothList = new LinkedList<String>();
    for(BluetoothDevice device : devices) {
        bluetoothList.add(device.getName());
    }
    return bluetoothList.toArray(new CharSequence[bluetoothList.size()]);
  }

  private void connectBluetooth(String bluetoothDevice) {

  }
}
