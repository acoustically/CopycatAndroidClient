package com.example.acoustically.copycat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.acoustically.copycat.bluetooth.SocketIOStream;


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
      buildTextview(layout);
      /*      try {
        mIOStream.getWriter().writeBoolean(true);
      } catch (Exception e) {
        Log.e("MYLOG", "Write Exception");
      }*/
    }
  }

  public LinearLayout buildLayout() {
    LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    linearLayout.setLayoutParams(
      new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
    setContentView((ScrollView) findViewById(R.id.PASTE_CONTENTS));
    return linearLayout;
  }

  public void buildTextview(LinearLayout layout) {
    countView++;
    TextView textView = new TextView(this);
    textView.setLayoutParams(new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    textView.setText("Afafsasa");
    layout.addView(textView);
    setContentView(layout);
  }
}