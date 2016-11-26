package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * Created by acoustically on 16. 11. 26.
 */
public class SocketIOStream{
  private static SocketIOStream instance;
  private BluetoothSocket mSocket;
  private BufferedOutputStream mWriter;
  private BufferedInputStream mReader;

  private SocketIOStream() {}

  public static SocketIOStream getInstance() {
    if (instance == null) {
      instance = new SocketIOStream();
    }
    return instance;
  }
  public void setIoSteam(BluetoothSocket socket) {
    try {
      mSocket = socket;
      mWriter = new BufferedOutputStream(mSocket.getOutputStream());
      mReader = new BufferedInputStream(mSocket.getInputStream());
    } catch (Exception e) {
      Log.e("MYLOG", "Socket IO Exception is occur");
    }
  }
  public void CloseSocket() throws Exception{
    mSocket.close();
    mWriter.close();
    mReader.close();
  }
}
