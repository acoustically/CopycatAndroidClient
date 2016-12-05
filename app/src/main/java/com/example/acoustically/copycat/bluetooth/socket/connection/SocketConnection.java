package com.example.acoustically.copycat.bluetooth.socket.connection;

import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

/**
 * Created by acoustically on 16. 11. 20.
 */
public class SocketConnection extends Thread{
  private BluetoothSocket mSocket;
  private OnSocketAvailableListener mSocketListener;
  private SocketIOStream mSocketIOStream;

  public SocketConnection(String name) {
    super(name);
  }
  public void setSocket(BluetoothSocket socket) {
    mSocket = socket;
  }

  @Override
  public void run() {
    try {
      mSocket.connect();
      if(mSocket.isConnected()) {
        Log.d("MYLOG", "socket is openned");
        mSocketListener.sendMessage(Message.obtain());
        mSocketIOStream.setIoSteam(mSocket);
      }
    } catch(Exception e) {
      Log.e("MYLOG", "Socket is not open");
    }
  }
  public void setSocketAvailableListener(OnSocketAvailableListener listener) {
    mSocketListener = listener;
  }
  public void setSocketIOStream(SocketIOStream socketIOStream) {
    mSocketIOStream = socketIOStream;
  }
}
