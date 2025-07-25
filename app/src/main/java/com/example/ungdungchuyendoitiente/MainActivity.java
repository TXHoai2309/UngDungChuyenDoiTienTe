package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Activity cho chức năng đăng nhập
public class MainActivity extends AppCompatActivity {

    TextView tvDangKy;
    EditText edtUid, edtPassword;
    TextView tvKhach;

    DataBaseHelper_DangKy db;

    private Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view từ layout
        edtUid = findViewById(R.id.edtUid);
        edtPassword = findViewById(R.id.edtPassword);
        tvKhach = findViewById(R.id.tvKhach);
        btnDangNhap = findViewById(R.id.btnLogin);

        // Khởi tạo database helper
        db = new DataBaseHelper_DangKy(this);

        // Xử lý sự kiện khi nhấn vào "Đăng ký"
        tvDangKy = findViewById(R.id.tvdangky);
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình đăng ký
                Intent intent = new Intent(MainActivity.this,DangKy.class);
                startActivity(intent);
            }
        });

        // Nếu vừa đăng ký xong, tự động điền id và password vào form đăng nhập
        Intent intent = getIntent();
        if(intent!=null){
            String id = intent.getStringExtra("id");
            String psw = intent.getStringExtra("psw");

            if(id!=null && psw!=null){
                edtUid.setText(id);
                edtPassword.setText(psw);
            }
        }

        // Xử lý sự kiện khi nhấn nút Đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtUid.getText().toString();
                String psw = edtPassword.getText().toString();

                // Kiểm tra nhập đủ thông tin
                if (id.isEmpty() || psw.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your login information", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra thông tin đăng nhập trong database
                    if (db.checkUser(id, psw)) {
                        // Lấy toàn bộ thông tin người dùng từ cơ sở dữ liệu
                        ThongTinDangKy userInfo = db.getUserInfo(id);

                        // Lưu toàn bộ thông tin người dùng vào SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", userInfo.getHoTen());  // Lưu họ tên vào SharedPreferences thay vì "Khách"
                        editor.putString("uid", userInfo.getUid());
                        editor.putString("hoten", userInfo.getHoTen());
                        editor.putString("matkhau", userInfo.getPsw());
                        editor.putString("email", userInfo.getEmail());
                        editor.putString("sdt", userInfo.getSdt());
                        editor.apply();

                        // Hiển thị thông báo đăng nhập thành công
                        Toast.makeText(MainActivity.this, "Log In Successful!", Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình tiếp theo
                        Intent intent = new Intent(MainActivity.this, Convert.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect login information", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Đăng nhập với tư cách khách (không cần tài khoản)
        tvKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("username", "Guest");  // Lưu tên là "Khách"
                editor.putString("email", "khach@example.com");  // Email mặc định
                editor.putString("sdt", "0000000000");           // Số điện thoại mặc định
                editor.putString("password", "");                // Mật khẩu mặc định
                editor.apply();

                // Chuyển sang màn hình cài đặt
                Intent intent = new Intent(MainActivity.this, CaiDat.class);
                startActivity(intent);
                finish();
            }
        });

    }
}