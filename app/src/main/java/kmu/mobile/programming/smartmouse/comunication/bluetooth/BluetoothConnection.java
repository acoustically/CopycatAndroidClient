package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import kmu.mobile.programming.smartmouse.MainActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by acoustically on 16. 11. 18.
 */
public class BluetoothConnection {
  final static UUID SERIAL_PORT_SERVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
  private static BluetoothConnection instance;
  private BluetoothCommunication mCommunication;
  private BluetoothManager mManager;
  private BluetoothAdapter mAdapter;
  private MainActivity mActivity;

  public static BluetoothConnection getInstance(MainActivity activity
                                                , BluetoothCommunication communication) {
    if (instance == null) {
      instance = new BluetoothConnection(activity, communication);
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
        try {
          mCommunication.setSocket(getSocket(bluetoothList[i].toString()));
        } catch (Exception e) {
          Log.e("MYLOG", "Socket don't open");
        }
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private BluetoothConnection(MainActivity mainActivity, BluetoothCommunication communication) {
    mActivity = mainActivity;
    mManager = (BluetoothManager)mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
    mAdapter = getAdapter();
    mCommunication = communication;
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
    List<String> bluetoothList = new LinkedList<String>();
    for(BluetoothDevice device : mAdapter.getBondedDevices()) {
        bluetoothList.add(device.getName());
    }
    return bluetoothList.toArray(new CharSequence[bluetoothList.size()]);
  }
  BluetoothDevice getDevice(String name) {
    for(BluetoothDevice device : mAdapter.getBondedDevices()) {
      if(name.equals(device.getName())) {
        return device;
      }
    }
    return null;
  }

  public BluetoothSocket getSocket(String bluetoothDevice) throws Exception{
    Log.e("MYLOG", getDevice(bluetoothDevice).getName() + " " + getDevice(bluetoothDevice).getAddress());
    return getDevice(bluetoothDevice).createRfcommSocketToServiceRecord(SERIAL_PORT_SERVICE_UUID);
  }
}
