package com.example.acoustically.copycat;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowDataActivity extends AppCompatActivity {
  private String mStringData;
  private Image mImageData;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_data);
    Intent intent = getIntent();
    mStringData = intent.getStringExtra("StringData");
    mImageData = (Image)intent.getSerializableExtra("ImageData");
    if(mImageData == null) {
      buildTextView();
    } else if(mStringData == null){

    }
  }
  private void buildTextView() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(20, 20, 10, 10);
    TextView textView = new TextView(this);
    textView.setText(mStringData);
    textView.setPadding(10, 10 ,10 ,10);
    textView.setLayoutParams(layoutParams);
    LinearLayout layout = (LinearLayout)findViewById(R.id.TextDataView);
    layout.addView(textView);
  }
}
