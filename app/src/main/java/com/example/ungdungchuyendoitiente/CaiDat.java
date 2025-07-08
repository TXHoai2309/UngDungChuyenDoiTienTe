package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CaiDat extends AppCompatActivity {

    TextView Logout, txtUser;
    ImageView imgAccount;
    Button btnGray, btnWhite;

    ImageView imgLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cai_dat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtUser = findViewById(R.id.txtUser);
        imgAccount = findViewById(R.id.imgAccount);
        btnWhite = findViewById(R.id.btnWhite);
        btnGray = findViewById(R.id.btnGrey);
        Logout = findViewById(R.id.txtLogout);
        imgLogout = findViewById(R.id.imgLogout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_setting);

        // Hiển thị tên người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");  // Lấy tên người dùng từ SharedPreferences, nếu không có thì dùng "Khách"
        txtUser.setText(username);
        //

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa toàn bộ dữ liệu trong SharedPreferences khi Logout
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Xóa tất cả dữ liệu trong SharedPreferences
                editor.apply();  // Áp dụng thay đổi

                Intent intent = new Intent(CaiDat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa toàn bộ dữ liệu trong SharedPreferences khi Logout
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Xóa tất cả dữ liệu trong SharedPreferences
                editor.apply();  // Áp dụng thay đổi

                Intent intent = new Intent(CaiDat.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                return true;
            } else if (item.getItemId() == R.id.bottom_convert) {
                // Chuyển sang màn Convert
                Intent intent = new Intent(CaiDat.this, Convert.class);
                startActivity(intent);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_news) {
                Intent intent = new Intent(CaiDat.this, NewsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        // Hết phần menu

        // Chuyển sang Hồ sơ
        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy userId hoặc username từ SharedPreferences
                // Lấy userId từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                String userId = sharedPreferences.getString("username", "Unknown User");


                // Chuyển sang ProfileActivity và truyền userId
                Intent intent = new Intent(CaiDat.this, Profile.class);
                intent.putExtra("userId", userId);  // Truyền userId vào ProfileActivity
                startActivity(intent);
            }
        });
    }
}