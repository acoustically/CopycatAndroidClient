package com.example.acoustically.copycat.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by acoustically on 16. 11. 26.
 */
public class SocketIOStream{
  private static SocketIOStream instance;
  private BluetoothSocket mSocket;
  private ObjectOutputStream mWriter;
  private ObjectInputStream mReader;

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
      mWriter = new ObjectOutputStream(mSocket.getOutputStream());
      mReader = new ObjectInputStream(mSocket.getInputStream());
    } catch (Exception e) {
      Log.e("MYLOG", "Socket IO Exception is occur");
    }
  }
  public void CloseSocket() throws Exception{
    mSocket.close();
    mWriter.close();
    mReader.close();
  }

  public ObjectOutputStream getWriter() {
    return mWriter;
  }

  public ObjectInputStream getReader() {
    return mReader;
  }

  public BluetoothSocket getSocket() {
    return mSocket;
  }
}
