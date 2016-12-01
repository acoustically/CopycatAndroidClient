package com.example.acoustically.copycat.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

/**
 * Created by acoustically on 16. 11. 28.
 */

public class ImageData implements Data{
  private Bitmap mBitmap;
  private LinearLayout mLayout;
  private Context mContext;
  private ImageView mImageView;

  public ImageData(Bitmap mBitmap, LinearLayout mLayout, Context mContext) {
    this.mBitmap = mBitmap;
    this.mLayout = mLayout;
    this.mContext = mContext;
    mImageView = new ImageView(mContext);
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
        Intent intent = new Intent(mContext, ShowImageActivity.class);
        intent.putExtra("ImageData", bitmapToBytes(mBitmap));
        mContext.startActivity(intent);
      }
    });
    mLayout.addView(mImageView);
  }
  private byte[] bitmapToBytes(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    return stream.toByteArray();
  }
}
