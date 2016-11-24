package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

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
    openSocket();
  }
  private void openSocket() throws Exception{
    mSocket.connect();
/*    writer = new BufferedOutputStream(mSocket.getOutputStream());
    reader = new BufferedInputStream(mSocket.getInputStream());*/
    Log.d("MYLOG", "socket openned");
  }
  public void closeSocket() throws Exception{
    mSocket.close();
    writer.close();
    reader.close();
  }
}
