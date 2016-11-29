package com.example.acoustically.copycat.bluetooth;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ObjectInputStream;

/**
 * Created by acoustically on 16. 11. 28.
 */

public class ReadThread extends Thread {
  public final static int STRING_DATA = 1;
  public final static int BITMAP_DATA = 0;
  private SocketIOStream mIOStream = SocketIOStream.getInstance();
  private Handler handler;
  private String stringData;

  public ReadThread(Handler handler) {
    this.handler = handler;
  }

  @Override
  public void run() {
    super.run();
    while(mIOStream.getSocket().isConnected()) {
      try {
        stringData = (String) mIOStream.getReader().readObject();
        post(stringData);
      } catch (Exception e) {
        Log.e("MYLOG", "bluetooth read error");
      }
    }
  }
  public void post(String data) {
    Message msg = Message.obtain();
    msg.what = STRING_DATA;
    handler.sendMessage(msg);
  }

  public String getStringData() {
    return stringData;
  }
}
