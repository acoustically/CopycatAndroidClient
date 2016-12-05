package com.example.acoustically.copycat.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.*;
import android.util.Log;
import android.widget.Toast;
import com.example.acoustically.copycat.MainActivity;
import com.example.acoustically.copycat.bluetooth.socket.connection.OnSocketAvailableListener;
import com.example.acoustically.copycat.bluetooth.socket.connection.SocketConnection;
import com.example.acoustically.copycat.bluetooth.socket.connection.SocketIOStream;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by acoustically on 16. 11. 18.
 */

public class BluetoothConnection {
  final static UUID SERIAL_PORT_SERVICE_UUID
    = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
  private SocketConnection mScoketConnect;
  private BluetoothAdapter mAdapter;
  private MainActivity mActivity;
  private List<String> mBluetoothList = new LinkedList<String>();
  private List<String> mBluetoothAddressList = new LinkedList<String>();
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction() == BluetoothDevice.ACTION_FOUND) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        Log.d("MYLOG", "device is searched" + " - " + device.getAddress());
        addBluetoothListNonRepeat(device);
      }
    }
  };

  public BluetoothConnection(MainActivity mainActivity, SocketConnection socketConnect) {
    mActivity = mainActivity;
    mAdapter = BluetoothAdapter.getDefaultAdapter();
    mScoketConnect = socketConnect;
    mActivity.registerReceiver(mBroadcastReceiver, new IntentFilter( BluetoothDevice.ACTION_FOUND ));
    setDiscoverable();
  }

  public void resultBluetoothOn(int requestCode, int resultCode) {
    if (requestCode == MainActivity.BLUETOOTH_ON) {
      if (resultCode == MainActivity.RESULT_OK) {
        Toast.makeText(mActivity, "Bluetooth is on", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(mActivity, "Bluetooth isn't on", Toast.LENGTH_SHORT).show();
      }
    }
  }

  public void selectBluetooth() {
    bluetoothOn();
    startDiscovery();

    mScoketConnect.setSocketAvailableListener(
            new OnSocketAvailableListener(mActivity));
    mScoketConnect.setSocketIOStream(SocketIOStream.getInstance());

    final CharSequence[] bluetoothList = convertListToCharSequence();
    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
    builder.setTitle("Bluetooth List");
    builder.setItems(bluetoothList, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Log.d("MYLOG", mAdapter.getRemoteDevice(mBluetoothAddressList.get(i)).getAddress());
        Log.d("MYLOG", mAdapter.getRemoteDevice(mBluetoothAddressList.get(i)).getName());
        mAdapter.cancelDiscovery();
        try {
          mScoketConnect.setSocket(getSocket(mAdapter.getRemoteDevice(mBluetoothAddressList.get(i))));
          mScoketConnect.start();
        } catch (Exception e) {
          Log.e("MYLOG", "has not socket");
        }
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private void bluetoothOn() {
    if(mAdapter.isEnabled()) {
      Log.d("MYLOG", "bluetooth is Enble");
    } else {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      mActivity.startActivityForResult(intent, MainActivity.BLUETOOTH_ON);
    }
  }

  private CharSequence[] convertListToCharSequence() {
    return mBluetoothList.toArray(new CharSequence[mBluetoothList.size()]);
  }

  private void addBluetoothListFromBonded() {
    for(BluetoothDevice device : mAdapter.getBondedDevices()) {
      addBluetoothListNonRepeat(device);
    }
  }

  private void addBluetoothListNonRepeat(BluetoothDevice device) {
    Iterator<String> devices = mBluetoothList.iterator();
    int count = 0;
    while(devices.hasNext()) {
      if (devices.next().equals(device.getName() + " " + device.getAddress())) {
        count++;
      }
    }
    if(count == 0) {
      mBluetoothList.add(device.getName() + " " + device.getAddress());
      mBluetoothAddressList.add(device.getAddress());
    }
  }

  private void startDiscovery() {
    mAdapter.cancelDiscovery();
    mAdapter.startDiscovery();
    addBluetoothListFromBonded();
  }

  public BluetoothSocket getSocket(BluetoothDevice device) throws Exception{
    Log.d("MYLOG", device.getName() + " " + device.getAddress());
    return device.createInsecureRfcommSocketToServiceRecord(SERIAL_PORT_SERVICE_UUID);
  }

  public void unRegistReceiver() {
    mActivity.unregisterReceiver(mBroadcastReceiver);
  }

  public void setDiscoverable() {
    if( mAdapter.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE )
      return;
    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
    mActivity.startActivity(intent);
  }
}
