package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by acoustically on 16. 11. 20.
 */
public class BluetoothCommunication extends Thread{
  private BluetoothSocket mSocket;
  private BufferedOutputStream mWriter;
  private BufferedInputStream mReader;
  private List<ListenerInterface> mListener = new LinkedList<>();
  public void setSocket(BluetoothSocket socket) {
    mSocket = socket;
  }
  @Override
  public void run() {
    try {
      mSocket.connect();
      mWriter = new BufferedOutputStream(mSocket.getOutputStream());
      mReader = new BufferedInputStream(mSocket.getInputStream());
      Log.e("MYLOG", "socket is openned");
      noti();
      //Toast.makeText(mActivity, "Socket is openned", Toast.LENGTH_SHORT).show();
    } catch(Exception e) {
      Log.e("MYLOG", "Socket is not open");
      //Toast.makeText(mActivity, "Socket is not opened", Toast.LENGTH_SHORT).show();
    }
  }
  public void addListener(ListenerInterface listener) {
    mListener.add(listener);
  }

  public void closeSocket() throws Exception{
    mSocket.close();
    mWriter.close();
    mReader.close();
  }

  private void noti() {
    Iterator<ListenerInterface> iterator = mListener.iterator();
    while(iterator.hasNext()) {
      iterator.next().update();
    }
  }
}
