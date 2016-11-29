package com.example.acoustically.copycat.bluetooth;

import android.util.Log;

import java.io.ObjectOutputStream;

/**
 * Created by acoustically on 16. 11. 28.
 */

public class WriteThread extends Thread {
  private SocketIOStream mIOStream = SocketIOStream.getInstance();
  // String : true , Bitmap : false

  @Override
  public void run() {
    super.run();
    if (mIOStream.getSocket().isConnected()) {
      try {
        mIOStream.getWriter().writeInt(1);
      } catch (Exception e) {
        Log.e("MYLOG", "bluetooth write error");
      }
    }
  }
}
