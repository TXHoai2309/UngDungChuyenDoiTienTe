package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

// Activity cho chức năng đăng ký tài khoản mới
public class DangKy extends AppCompatActivity {
    // Khai báo các EditText và Button
    private EditText edtUid,edtHoten,edtPsw,edtEmail,edtSdt;
    private Button btnDangKy;

    DataBaseHelper_DangKy db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view từ layout
        edtUid = findViewById(R.id.edtuid2);
        edtHoten = findViewById(R.id.edthoten);
        edtPsw = findViewById(R.id.edtpsw2);
        edtEmail = findViewById(R.id.edtemail);
        edtSdt = findViewById(R.id.edtsdt);
        btnDangKy = findViewById(R.id.btndangky);

        // Khởi tạo database helper
        db = new DataBaseHelper_DangKy(this);


        // Xử lý sự kiện khi nhấn nút Đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập
                String id = edtUid.getText().toString();
                String hoTen = edtHoten.getText().toString();
                String psw = edtPsw.getText().toString();
                String email = edtEmail.getText().toString();
                String sdt = edtSdt.getText().toString();


                // Kiểm tra nếu các trường không rỗng
                if (id.isEmpty() || hoTen.isEmpty() || psw.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
                    Toast.makeText(DangKy.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                } else if(db.isUserIdExists(id)) {
                    Toast.makeText(DangKy.this, "ID Existed!", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        // Thêm người dùng vào cơ sở dữ liệu
                        db.addUserDangKy(new ThongTinDangKy(id, hoTen, psw, email, sdt));

                        // Hiển thị thông báo thành công
                        Toast.makeText(DangKy.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();

                        // Chuyển đến màn hình đăng nhập, truyền id và password vừa đăng ký
                        Intent intent = new Intent(DangKy.this, MainActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("psw",psw);
                        setResult(RESULT_OK,intent);
                        startActivity(intent);
                    }
                }
        });
    }
}