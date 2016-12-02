package com.example.acoustically.copycat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.acoustically.copycat.bluetooth.ReadThread;
import com.example.acoustically.copycat.bluetooth.WriteThread;
import com.example.acoustically.copycat.data.Data;
import com.example.acoustically.copycat.data.ImageData;
import com.example.acoustically.copycat.data.StringData;


public class ClipBoardActivity extends AppCompatActivity {
  private Context mContext = this;
  private int countView = 0;
  private LinearLayout layout;
  private ReadThread readThread;
  private Button mGetButton;
  private Handler readHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      Data data;
      if (countView % 2 == 0) {
        layout = buildLayout();
      }
      if(msg.what == ReadThread.STRING_DATA) {
        data = new StringData(byteToString(msg), layout, mContext);
      } else {
        data = new ImageData(byteToBitmap(msg), layout, mContext);
      }
      buildView(data);
      setButtonEnavle(true);
    }
    private String byteToString(Message msg) {
      return new String((byte[])msg.obj, 0, msg.arg1);
    }
    private Bitmap byteToBitmap(Message msg) {
      Bitmap bitmap = BitmapFactory.decodeByteArray((byte[]) msg.obj, 0, msg.arg1);
      Log.e("MYLOG", (bitmap + ""));
      return bitmap;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clip_board);
    mGetButton = (Button)findViewById(R.id.PASTE);
    setButtonEnavle(true);
    readThread = new ReadThread(readHandler);
    readThread.start();
  }

  public void onClick(View view) {
    if (view.getId() == R.id.PASTE) {
      WriteThread writeThread = new WriteThread();
      writeThread.start();
      setButtonEnavle(false);
    }
  }
  private void setButtonEnavle(boolean enavle) {
    if (enavle) {
      mGetButton.setText("get");
      mGetButton.setTextColor(Color.rgb(0,0,0));
      mGetButton.setEnabled(true);
    } else {
      mGetButton.setText("copying");
      mGetButton.setTextColor(Color.rgb(155,155,155));
      mGetButton.setEnabled(false);
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