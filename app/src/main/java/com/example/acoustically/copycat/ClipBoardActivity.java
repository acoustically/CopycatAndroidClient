package com.example.acoustically.copycat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.acoustically.copycat.bluetooth.SocketIOStream;
import com.example.acoustically.copycat.data.Data;
import com.example.acoustically.copycat.data.StringData;


public class ClipBoardActivity extends AppCompatActivity {
  private SocketIOStream mIOStream;
  private int countView = 0;
  private LinearLayout layout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clip_board);
    mIOStream = SocketIOStream.getInstance();
  }

  public void onClick(View view) {
    if (view.getId() == R.id.PASTE) {
      if (countView % 2 == 0) {
        layout = buildLayout();
      }
      Data data;
      if (true) {
        data = new StringData("test", layout, this);
      }
      buildView(data);
      /*      try {
        mIOStream.getWriter().writeBoolean(true);
      } catch (Exception e) {
        Log.e("MYLOG", "Write Exception");
      }*/
    }
  }

  public LinearLayout buildLayout() {
    final int height = (int) TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, height);
    layoutParams.setMargins(10, 10, 10, 10);
    linearLayout.setLayoutParams(layoutParams);
    LinearLayout parent = (LinearLayout)findViewById(R.id.PASTE_CONTENTS);
    parent.addView(linearLayout);
    return linearLayout;
  }

  public void buildView(Data data) {
    countView++;
    data.showDataInView();
  }
}