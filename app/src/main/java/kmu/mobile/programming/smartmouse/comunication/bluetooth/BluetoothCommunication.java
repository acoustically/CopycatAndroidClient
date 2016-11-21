package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * Created by acoustically on 16. 11. 20.
 */
public class BluetoothCommunication {
  private BluetoothSocket mSocket;
  private BufferedOutputStream writer;
  private BufferedInputStream reader;

  public void setSocket(BluetoothSocket socket) throws Exception{
    mSocket = socket;
    writer = new BufferedOutputStream(mSocket.getOutputStream());
    reader = new BufferedInputStream(mSocket.getInputStream());
  }
}
