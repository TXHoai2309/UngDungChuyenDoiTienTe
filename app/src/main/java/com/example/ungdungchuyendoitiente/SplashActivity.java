package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // Sử dụng layout với logo của bạn

        // Đợi 5 giây trước khi chuyển sang MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Sau 5 giây, chuyển sang MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity để không quay lại màn hình splash
            }
        }, 2000);  // Delay 5 giây (5000 milliseconds)
    }
}
