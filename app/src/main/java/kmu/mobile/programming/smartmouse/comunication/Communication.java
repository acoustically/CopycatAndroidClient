package kmu.mobile.programming.smartmouse.comunication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by acoustically on 16. 11. 18.
 */
public abstract class Communication {
  protected BufferedOutputStream mWriter;
  protected BufferedInputStream mReader;

  public Communication(OutputStream writer, InputStream reader) {
    mWriter = new BufferedOutputStream(writer);
    mReader = new BufferedInputStream(reader);
  }

  abstract void send();
  abstract void receive();
}
