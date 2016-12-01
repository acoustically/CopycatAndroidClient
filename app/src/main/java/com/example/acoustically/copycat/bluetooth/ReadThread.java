package com.example.acoustically.copycat.bluetooth;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.R.attr.bitmap;

/**
 * Created by acoustically on 16. 11. 28.
 */

public class ReadThread extends Thread {
  public final static int STRING_DATA = 1;
  public final static int BITMAP_DATA = 2;
  private SocketIOStream mIOStream = SocketIOStream.getInstance();
  private Handler handler;
  public ReadThread(Handler handler) {
    this.handler = handler;
  }

  @Override
  public void run() {
    super.run();
    boolean stay = true;
    int whatItem = 0;
    int length = 0;
    int totalLangth = 0;
    byte[] readBuffer = new byte[2160 * 3840];
    while(mIOStream.getSocket().isConnected()) {
      try {
        if(stay) {
          whatItem = mIOStream.getReader().read();
          Log.e("MYLOG", whatItem + " : whatItem");
          byte[] strBuffer = new byte[100];
          int strLangth = mIOStream.getReader().read(strBuffer);
          String strTotalLangth = new String(strBuffer, 0, strLangth);
          totalLangth = Integer.parseInt(strTotalLangth);
          Log.e("MYLOG", totalLangth + " = totalLangth");
        }
        if (whatItem == 1) {
          length = mIOStream.getReader().read(readBuffer);
          post(whatItem, readBuffer, length);
          length = 0;
        } else if (whatItem == 2 || !stay) {
          stay = false;
          Log.e("MYLOG", "-----------");
          byte[] tempBuffer = new byte[2000];
          int tempLength = mIOStream.getReader().read(tempBuffer);
          chargeBuffer(readBuffer, length, tempBuffer, tempLength);
          length += tempLength;
          Log.e("MYLOG", length + " --> current langth");
          if (totalLangth == length) {
            post(whatItem, readBuffer, length);
            stay = true;
            length = 0;
          }
        }
      } catch (Exception e) {
        Log.e("MYLOG", "bluetooth read error");
        return;
      }
    }
  }
  private void chargeBuffer(byte[] buffer, int length, byte[] temp, int tempLength) {
    for (int i = 0; i < tempLength; i++) {
      buffer[length + i] = temp[i];
    }
  }
  private void post(int whatItem, byte[] data, int length) {
    Message msg = Message.obtain();
    msg.what = whatItem;
    msg.obj = data;
    msg.arg1 = length;
    handler.sendMessage(msg);
    Log.e("MYLOG", "Sended to handler");
  }
  private void test(byte[] temp, int length) {
    int count = 0;
    Log.e("MYLOG", "-----------");
    for (int i = 0; i < length; i+=100) {
      Log.e("MYLOG", temp[i] + " " + count++);
    }
    Log.e("MYLOG", "-----------");
  }
}
