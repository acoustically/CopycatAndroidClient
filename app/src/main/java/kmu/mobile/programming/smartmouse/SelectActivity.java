package kmu.mobile.programming.smartmouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {
  Intent intent;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select);
  }

  public void onClick(View view) {
    if (view.getId() == R.id.EnterSensitivityMode) {
      intent = new Intent(this, SettingActivity.class);
      startActivity(intent);
    } else if (view.getId() == R.id.EnterMouseMode) {
      intent = new Intent(this, MouseModeActivity.class);
      startActivity(intent);
    }
  }
}
