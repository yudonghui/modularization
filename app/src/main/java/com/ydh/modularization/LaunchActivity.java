package com.ydh.modularization;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class LaunchActivity extends RxAppCompatActivity {
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        Class<?> aClass = Class.forName("com.ydh.module_first.activity.FirstActivity");
                        startActivity(new Intent(LaunchActivity.this, aClass));
                        finish();
                    } catch (ClassNotFoundException e) {

                        Toast.makeText(LaunchActivity.this, "没有发现FirstActivity", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mHandler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) mHandler = null;
    }
}
