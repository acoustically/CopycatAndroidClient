package com.example.acoustically.copycat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.cacheColorHint;
import static android.R.attr.name;

public class ShowImageActivity extends AppCompatActivity {
  private Bitmap mBitmapData;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_image);
    Intent intent = getIntent();
    byte[] imageByteData = intent.getByteArrayExtra("ImageData");
    mBitmapData = BitmapFactory.decodeByteArray(imageByteData, 0, imageByteData.length);
    buildImageView(mBitmapData);
  }
  private void buildImageView(Bitmap bitmap) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(20, 20, 10, 10);
    ImageView imageView = new ImageView(this);
    imageView.setImageBitmap(bitmap);
    imageView.setPadding(10, 10 ,10 ,10);
    imageView.setLayoutParams(layoutParams);
    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    LinearLayout layout = (LinearLayout)findViewById(R.id.TextDataView);
    layout.addView(imageView);
  }

  public void onClick(View view) {
    if(view.getId() == R.id.ImageCopyButton) {
      creatFileWithBitmap();
      Toast.makeText(this, "Image Download", Toast.LENGTH_LONG).show();
    }
  }
  private void creatFileWithBitmap() {
    String storage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
    String file_name = getCurrentTime() + ".png";
    Log.e("MYLOG", storage + file_name);
    File file = new File(storage + file_name);
    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      mBitmapData.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
      outputStream.close();
    } catch (FileNotFoundException e) {
      Log.e("MYLOG", "File is not exist");
    } catch (Exception e ){
      Log.e("MYLOG", "IO Exception");
    }
  }
  private String getCurrentTime() {
    long time = System.currentTimeMillis();
    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
    return dayTime.format(new Date(time));
  }
}
