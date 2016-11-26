package kmu.mobile.programming.smartmouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectModeActivity extends AppCompatActivity {
  Intent intent;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_mode);
  }

  public void onClick(View view) {
    if (view.getId() == R.id.EnterSensitivityMode) {
      intent = new Intent(this, SensitivityControlActivity.class);
      startActivity(intent);
    } else if (view.getId() == R.id.EnterMouseMode) {

    }
  }
}
