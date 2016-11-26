package kmu.mobile.programming.smartmouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MouseModeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mouse);
  }

  public void onClick(View view) {
    if(view.getId() == R.id.LeftClick) {

    } else if (view.getId() == R.id.RigthClick) {

    }
  }
}
