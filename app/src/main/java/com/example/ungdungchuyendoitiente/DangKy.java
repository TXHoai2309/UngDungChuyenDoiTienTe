package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class DangKy extends AppCompatActivity {
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

        edtUid = findViewById(R.id.edtuid2);
        edtHoten = findViewById(R.id.edthoten);
        edtPsw = findViewById(R.id.edtpsw2);
        edtEmail = findViewById(R.id.edtemail);
        edtSdt = findViewById(R.id.edtsdt);
        btnDangKy = findViewById(R.id.btndangky);

        db = new DataBaseHelper_DangKy(this);


        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtUid.getText().toString();
                String hoTen = edtHoten.getText().toString();
                String psw = edtPsw.getText().toString();
                String email = edtEmail.getText().toString();
                String sdt = edtSdt.getText().toString();


                // Kiểm tra nếu các trường không rỗng
                if (id.isEmpty() || hoTen.isEmpty() || psw.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
                    Toast.makeText(DangKy.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm người dùng vào cơ sở dữ liệu
                    db.addUserDangKy(new ThongTinDangKy(id, hoTen, psw, email, sdt));

                    // Hiển thị thông báo thành công
                    Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển đến màn hình đăng nhập
                    Intent intent = new Intent(DangKy.this, MainActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("psw",psw);
                    setResult(RESULT_OK,intent);
                    startActivity(intent);
                    finish();
                }

            }
        });



    }


}