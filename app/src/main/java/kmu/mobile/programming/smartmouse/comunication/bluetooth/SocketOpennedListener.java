package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.content.Intent;
import kmu.mobile.programming.smartmouse.MainActivity;
import kmu.mobile.programming.smartmouse.SelectModeActivity;

/**
 * Created by acoustically on 16. 11. 25.
 */
public class SocketOpennedListener implements ListenerInterface{
  private MainActivity mainActivity;

  public SocketOpennedListener(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @Override
  public void update() {
    Intent intent = new Intent(mainActivity, SelectModeActivity.class);
    mainActivity.startActivity(intent);
  }
}
