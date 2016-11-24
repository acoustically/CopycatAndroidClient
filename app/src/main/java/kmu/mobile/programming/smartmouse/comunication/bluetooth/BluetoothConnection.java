package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.*;
import android.content.*;
import android.util.Log;
import android.widget.Toast;
import kmu.mobile.programming.smartmouse.MainActivity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by acoustically on 16. 11. 18.
 */
public class BluetoothConnection {
  final static UUID SERIAL_PORT_SERVICE_UUID = UUID.fromString("0000110a-0000-1000-8000-00805f9b34fb");
  private BluetoothCommunication mCommunication;
  private BluetoothAdapter mAdapter;
  private MainActivity mActivity;
  private List<String> mBluetoothList = new LinkedList<String>();
  private List<String> mBluetoothAddressList = new LinkedList<String>();
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction() == BluetoothDevice.ACTION_FOUND) {
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        Log.e("MYLOG", "device is searched" + " - " + device.getAddress());
        if (device.getBondState() != BluetoothDevice.BOND_BONDED) ;{
          addBluetoothListNonRepeat(device);
        }
      }
    }
  };

  public BluetoothConnection(MainActivity mainActivity, BluetoothCommunication communication) {
    mActivity = mainActivity;
    mAdapter = BluetoothAdapter.getDefaultAdapter();
    mCommunication = communication;
    mActivity.registerReceiver(mBroadcastReceiver, new IntentFilter( BluetoothDevice.ACTION_FOUND ));
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
    mAdapter.cancelDiscovery();
    mAdapter.startDiscovery();
    addBluetoothListFromBonded();
    bluetoothOn();
    final CharSequence[] bluetoothList = convertListToCharSequence();
    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
    builder.setTitle("Bluetooth List");
    builder.setItems(bluetoothList, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        try {
          mCommunication.setSocket(
            getSocket(mAdapter.getRemoteDevice(mBluetoothAddressList.get(i))));
        } catch (Exception e) {
          Log.e("MYLOG", "Socket is not open");
        }
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  private void bluetoothOn() {
    if(mAdapter.isEnabled()) {
      Log.d("MYLOG", "bluetooth inEnble");
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

  public BluetoothSocket getSocket(BluetoothDevice device) throws Exception{
    Log.e("MYLOG", device.getName() + " " + device.getAddress());
    return device.createRfcommSocketToServiceRecord(SERIAL_PORT_SERVICE_UUID);
  }
}
