package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {
    private TextView tvUID2, tvHoten2, tvMatkhau2, tvemail2, tvSDT2;
    private DataBaseHelper_DangKy db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvUID2 = findViewById(R.id.tvUID2);
        tvHoten2 = findViewById(R.id.tvHoten2);
        tvMatkhau2 = findViewById(R.id.tvMatkhau2);
        tvemail2 = findViewById(R.id.tvemail2);
        tvSDT2 = findViewById(R.id.tvSDT2);

        db = new DataBaseHelper_DangKy(this);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        // Kiểm tra userId có hợp lệ không
        if (userId != null && !userId.isEmpty()) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            ThongTinDangKy userInfo = db.getUserInfo(userId);

            // Điền dữ liệu vào các TextView nếu có thông tin người dùng
            if (userInfo != null) {
                tvUID2.setText(userInfo.getUid());
                tvHoten2.setText(userInfo.getHoTen());
                tvMatkhau2.setText(userInfo.getPsw());
                tvemail2.setText(userInfo.getEmail());
                tvSDT2.setText(userInfo.getSdt());
            } else {
                // Nếu không tìm thấy thông tin người dùng, hiển thị thông báo rõ ràng hơn
                tvUID2.setText("Không tìm thấy User");
                tvHoten2.setText("Không tìm thấy Họ tên");
                tvMatkhau2.setText("Không tìm thấy Mật khẩu");
                tvemail2.setText("Không tìm thấy Email");
                tvSDT2.setText("Không tìm thấy SĐT");
            }
        } else {
            // Nếu userId không hợp lệ, có thể chuyển hướng hoặc thông báo lỗi
            tvUID2.setText("Lỗi: Không có ID người dùng");
        }
    }
}