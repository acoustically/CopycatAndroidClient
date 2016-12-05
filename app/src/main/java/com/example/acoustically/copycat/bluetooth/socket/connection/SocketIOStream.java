package com.example.acoustically.copycat.bluetooth.socket.connection;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by acoustically on 16. 11. 26.
 */
public class SocketIOStream{
  private static SocketIOStream instance;
  private BluetoothSocket mSocket;
  private OutputStream mWriter;
  private InputStream mReader;

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
      mWriter = mSocket.getOutputStream();
      mReader = mSocket.getInputStream();
    } catch (Exception e) {
      Log.e("MYLOG", "Socket IO Exception is occur");
    }
  }
  public void CloseSocket() throws Exception{
    mSocket.close();
    mWriter.close();
    mReader.close();
  }

  public OutputStream getWriter() {
    return mWriter;
  }

  public InputStream getReader() {
    return mReader;
  }

  public BluetoothSocket getSocket() {
    return mSocket;
  }
}
