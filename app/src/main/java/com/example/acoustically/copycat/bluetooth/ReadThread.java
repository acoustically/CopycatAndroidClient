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
  private String data;
  public ReadThread(Handler handler) {
    this.handler = handler;
  }

  @Override
  public void run() {
    super.run();
    while(mIOStream.getSocket().isConnected()) {
      try {
        byte[] readBuffer = new byte[1024];
        int length = mIOStream.getReader().read(readBuffer);
        data = new String(readBuffer, 0, length);
        Log.e("MYLOG", data + "");
        post();
      } catch (Exception e) {
        Log.e("MYLOG", "bluetooth read error");
      }
    }
  }
  private void post() {
    Message msg = Message.obtain();
    msg.what = STRING_DATA;
    msg.obj = data;
    handler.sendMessage(msg);
  }
}
