package com.example.acoustically.copycat.data;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.acoustically.copycat.R;
import com.example.acoustically.copycat.ShowStringActivity;

/**
 * Created by acoustically on 16. 11. 28.
 */

public class StringData implements Data{
  private String mString;
  private Context mContext;
  private TextView mTextView;
  private LinearLayout mLayout;

  public StringData(String mString, LinearLayout layout, Context mContext) {
    this.mString = mString;
    this.mContext = mContext;
    mTextView = new TextView(mContext);
    mLayout = layout;
  }

  @Override
  public void showDataInView() {
    final int width = (int) TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, 150, mContext.getResources().getDisplayMetrics());
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      width, ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.setMargins(20, 20, 10, 10);
    mTextView.setLayoutParams(layoutParams);
    mTextView.setText(mString);
    mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.xml_boader));
    mTextView.setPadding(10, 10, 10, 10);
    mTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(mContext, ShowStringActivity.class);
        intent.putExtra("StringData", mString);
        mContext.startActivity(intent);
      }
    });
    mLayout.addView(mTextView);
  }
}
