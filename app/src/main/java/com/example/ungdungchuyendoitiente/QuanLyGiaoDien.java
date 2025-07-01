package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuanLyGiaoDien extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_framelayout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_convert);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_convert) {
                // Mở Activity "ConvertActivity"
                Intent convertIntent = new Intent(QuanLyGiaoDien.this, Convert.class);
                startActivity(convertIntent);
                finish(); // Kết thúc QuanLyGiaoDien

                // Thay đổi màu sắc của icon và text cho mục 'Convert'
                bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.YELLOW));  // Thay đổi màu icon
                bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.YELLOW));    // Thay đổi màu chữ
            } else if (item.getItemId() == R.id.bottom_check) {
                // Xử lý cho mục 'Check'
            } else if (item.getItemId() == R.id.bottom_setting) {
                // Xử lý cho mục 'Setting'
            }
            return true;
        });
    }
}
