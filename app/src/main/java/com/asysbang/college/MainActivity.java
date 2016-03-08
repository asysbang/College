package com.asysbang.college;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    private int step = 0;

    //判断用户是否在动画过程中退出activity
    private boolean isPaused = false;

    private WelcomeView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new WelcomeView(this);
        setContentView(mView);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        l("MainActivity  onAttachedToWindow");
        mHandler.sendEmptyMessage(MSG_NEXT_STEP);
    }

    private void l(String s) {
        System.out.println("========= >>> " + s);
    }

    private static final int MSG_NEXT_STEP = 0;

    private static final int MSG_NEXT_ACTIVITY = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (MSG_NEXT_STEP == what) {
                step++;
                if (step < 20) {
                    mView.invalidate();
                    sendEmptyMessageDelayed(MSG_NEXT_STEP, 100);
                } else {
                    sendEmptyMessageDelayed(MSG_NEXT_ACTIVITY,1000);
                }
            } else if (MSG_NEXT_ACTIVITY == what && !isPaused) {
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                finish();
            }

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
        mHandler.removeMessages(MSG_NEXT_STEP);
        mHandler.removeMessages(MSG_NEXT_ACTIVITY);
        finish();
    }

    private class WelcomeView extends View {

        private Paint letterPaint;

        private float[] letterWidth = new float[7];

        public WelcomeView(Context context) {
            super(context);
            letterPaint = new Paint();
            letterPaint.setTextSize(160);
            letterPaint.setColor(Color.argb(255, 90, 88, 234));
            int w = letterPaint.getTextWidths("COLLEGE",0,7,letterWidth);
            l(" getWidth = "+getWidth());
            for (float s : letterWidth) {
                l("  float : "+ s);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            l("WelcomeView  onAttachedToWindow" + getWidth());
        }

        @Override
        protected void onDraw(Canvas canvas) {
            l("ondraw  step = " + step);
            int height = canvas.getHeight();
            int y = height/2;
            int width = canvas.getWidth();
            int total = 0;
            for (float s : letterWidth) {
                total+=s;
            }
            int deta = 30;
            int d = width - total - deta*4;
            int x = d/2;
            if(step < 20) {
                canvas.translate(-60 * step + width,0);
            }
            canvas.drawText("C", x, y, letterPaint);
            x += letterWidth[0]+deta;
            canvas.drawText("O", x, y, letterPaint);
            x += letterWidth[1]+deta;
            canvas.drawText("L", x, y, letterPaint);
            x += letterWidth[2]+deta;
            canvas.drawText("L", x, y, letterPaint);
            x += letterWidth[3]+deta;
            canvas.drawText("E", x, y, letterPaint);
            x += letterWidth[4]+deta;
            canvas.drawText("G", x, y, letterPaint);
            x += letterWidth[5]+deta;
            canvas.drawText("E", x, y, letterPaint);
        }
    }
}
