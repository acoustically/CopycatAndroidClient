package kmu.mobile.programming.smartmouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectModeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_mode);
  }

  public void onClick(View view) {
    if (view.getId() == R.id.EnterSensitivityMode) {

    } else if (view.getId() == R.id.EnterMouseMode) {

    }
  }
}
