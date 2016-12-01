package com.example.acoustically.copycat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ShowImageActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_image);
    Intent intent = getIntent();
    byte[] imageByteData = intent.getByteArrayExtra("ImageData");
    Bitmap bitmapData = BitmapFactory.decodeByteArray(imageByteData, 0, imageByteData.length);
    buildImageView(bitmapData);
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
}
