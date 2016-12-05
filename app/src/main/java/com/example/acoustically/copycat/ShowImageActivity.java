package com.example.acoustically.copycat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowImageActivity extends AppCompatActivity {
  private Bitmap mBitmapData;
  private String mFilePath;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_image);
    Intent intent = getIntent();
    mFilePath = intent.getStringExtra("ImageName");
    mBitmapData = fileToBitmap(mFilePath);
    buildImageView(mBitmapData);
  }
  private Bitmap fileToBitmap(String filePath) {
    return BitmapFactory.decodeFile(filePath);
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
    deleteFileToPath(mFilePath);
  }
  public void onClick(View view) {
    if(view.getId() == R.id.ImageCopyButton) {
      creatFileWithBitmap();
      Toast.makeText(this, "Image Download", Toast.LENGTH_LONG).show();
    }
  }
  private void deleteFileToPath(String mFilePath) {
    File file = new File(mFilePath);
    file.delete();
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
