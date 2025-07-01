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
        edtUid = findViewById(R.id.edtUid);
        edtPassword = findViewById(R.id.edtPassword);
        tvKhach = findViewById(R.id.tvKhach);
        btnDangNhap = findViewById(R.id.btnLogin);

        tvDangKy = findViewById(R.id.tvdangky);
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DangKy.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if(intent!=null){
            String id = intent.getStringExtra("id");
            String psw = intent.getStringExtra("psw");

            if(id!=null && psw!=null){
                edtUid.setText(id);
                edtPassword.setText(psw);
            }
        }


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtUid.getText().toString();
                String psw = edtPassword.getText().toString();

                if(id.isEmpty()||psw.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }else{
                    if(db.checkUser(id,psw)){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        // Chuyển sang màn hình chính của ứng dụng (chẳng hạn MainActivity)
                        Intent mainIntent = new Intent(MainActivity.this, Convert.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}