package kmu.mobile.programming.smartmouse.comunication.bluetooth;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import kmu.mobile.programming.smartmouse.MainActivity;
import kmu.mobile.programming.smartmouse.SelectModeActivity;

/**
 * Created by acoustically on 16. 11. 25.
 */
public class OnSocketAvailableListener extends Handler{
  final private MainActivity mActivity;

  public OnSocketAvailableListener(MainActivity activity) {
    mActivity = activity;
  }

  public void startSelectModeActivity() {
    Intent intent = new Intent(mActivity, SelectModeActivity.class);
    mActivity.startActivity(intent);
  }
  public void showToast(boolean isConnect) {
    if(isConnect == true) {
      Toast.makeText(mActivity, "Socket is openned", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(mActivity, "Socket is not opened", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void handleMessage(Message msg) {
    showToast(true);
    startSelectModeActivity();
  }
}
