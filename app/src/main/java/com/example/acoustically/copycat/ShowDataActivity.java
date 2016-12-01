package com.example.acoustically.copycat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDataActivity extends AppCompatActivity {
  String mStringData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_data);
    Intent intent = getIntent();
    mStringData = intent.getStringExtra("StringData");
    buildTextView(mStringData);
  }
  private void buildTextView(String string) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(20, 20, 10, 10);
    TextView textView = new TextView(this);
    textView.setText(string);
    textView.setPadding(10, 10 ,10 ,10);
    textView.setLayoutParams(layoutParams);
    LinearLayout layout = (LinearLayout)findViewById(R.id.TextDataView);
    layout.addView(textView);
  }

  public void onClick(View view) {
    if(view.getId() == R.id.TextCopyButton) {
      ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
      ClipData data = ClipData.newPlainText("CopyCat", mStringData);
      manager.setPrimaryClip(data);
      Toast.makeText(this, "Text Copy", Toast.LENGTH_LONG).show();
    } else if(view.getId() == R.id.TextDownloadButton) {
      creatFileWithBitmap();
      Toast.makeText(this, "Text Download", Toast.LENGTH_LONG).show();
    }
  }
  private void creatFileWithBitmap() {
    String storage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
    String file_name = getCurrentTime() + ".txt";
    Log.e("MYLOG", storage + file_name);
    File file = new File(storage + file_name);
    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      outputStream.write(mStringData.getBytes());
      outputStream.close();
    } catch (Exception e) {
      Log.e("MYLOG", "File out error");
    }
  }
  private String getCurrentTime() {
    long time = System.currentTimeMillis();
    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
    return dayTime.format(new Date(time));
  }
}
