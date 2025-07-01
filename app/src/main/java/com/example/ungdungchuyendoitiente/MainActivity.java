package com.example.ungdungchuyendoitiente;

import android.content.Intent;
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
                if(id.isEmpty()||psw.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }else{
                    // Kiểm tra thông tin đăng nhập trong database
                    if(db.checkUser(id,psw)){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Convert.class);
                        startActivity(intent);
                        finish();
                        // Có thể chuyển sang màn hình chính của app tại đây
                        Intent mainIntent = new Intent(MainActivity.this, Convert.class);
                        startActivity(mainIntent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Đăng nhập với tư cách khách (không cần tài khoản)
        tvKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CaiDat.class);
                startActivity(intent);
            }
        });

    }
}