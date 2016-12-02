package com.example.acoustically.copycat.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.acoustically.copycat.R;
import com.example.acoustically.copycat.ShowDataActivity;
import com.example.acoustically.copycat.ShowImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by acoustically on 16. 11. 28.
 */

public class ImageData implements Data{
  private Bitmap mBitmap;
  private LinearLayout mLayout;
  private Context mContext;
  private ImageView mImageView;
  private int fileNum = 0;

  public ImageData(Bitmap bitmap, LinearLayout mLayout, Context mContext) {
    this.mLayout = mLayout;
    this.mContext = mContext;
    mImageView = new ImageView(mContext);
    mBitmap = bitmap;
  }
  @Override
  public void showDataInView() {
    Log.e("MYLOG", mBitmap + "");
    final int width = (int) TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, 150, mContext.getResources().getDisplayMetrics());
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      width, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(20, 20, 10, 10);
    mImageView.setLayoutParams(layoutParams);
    mImageView.setImageBitmap(mBitmap);
    mImageView.setBackground(mContext.getResources().getDrawable(R.drawable.xml_boader));
    mImageView.setPadding(10, 10, 10, 10);
    mImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String path = makeImageFile(mBitmap);
        Intent intent = new Intent(mContext, ShowImageActivity.class);
        intent.putExtra("ImageName", path);
        mContext.startActivity(intent);
      }
    });
    mLayout.addView(mImageView);
  }
  private String makeImageFile(Bitmap bitmap) {
    String file_name = "/"+ fileNum + ".png";
    File file = new File(mContext.getCacheDir() + file_name);
    try {
      FileOutputStream outputStream = new FileOutputStream(file);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
      outputStream.close();
    } catch (FileNotFoundException e) {
      Log.e("MYLOG", "File is not exist");
    } catch (Exception e ){
      Log.e("MYLOG", "IO Exception");
    }
    return file.getPath();
  }
  private byte[] bitmapToBytes(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    return stream.toByteArray();
  }
}
