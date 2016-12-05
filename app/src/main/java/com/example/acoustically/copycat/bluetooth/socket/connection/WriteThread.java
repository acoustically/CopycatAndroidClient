package com.example.acoustically.copycat.bluetooth.socket.connection;

import android.util.Log;

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
        mIOStream.getWriter().write(10);
        Log.d("MYLOG", "send data");
      } catch (Exception e) {
        Log.e("MYLOG", "bluetooth write error");
      }
    }
  }
}
