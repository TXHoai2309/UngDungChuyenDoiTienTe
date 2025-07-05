package com.example.ungdungchuyendoitiente;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {
    private TextView tvUID2, tvHoten2, tvMatkhau2, tvemail2, tvSDT2;
    private ImageView imgBack2;
    private Button btnSua, btnXoa;
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
        imgBack2 = findViewById(R.id.imgBack2);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);

        // Khởi tạo đối tượng database helper
        db = new DataBaseHelper_DangKy(this);

        // Lấy thông tin người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String userId = sharedPreferences.getString("uid", "User not found");

        // Hiển thị thông tin người dùng
        ThongTinDangKy userInfo = db.getUserInfo(userId);
        if (userInfo != null) {
            tvUID2.setText(userInfo.getUid());
            tvHoten2.setText(userInfo.getHoTen());
            tvMatkhau2.setText(userInfo.getPsw());
            tvemail2.setText(userInfo.getEmail());
            tvSDT2.setText(userInfo.getSdt());
        }

        // Quay lại màn hình trước đó (Cài đặt hoặc MainActivity)
        imgBack2.setOnClickListener(v -> onBackPressed());

        // Hiển thị Dialog sửa thông tin
        btnSua.setOnClickListener(v -> showEditDialog(userId));

        // Xóa tài khoản (Chức năng thêm)
        btnXoa.setOnClickListener(v -> showDeleteDialog(userId));
    }

    // Hàm hiển thị Dialog sửa thông tin
    private void showEditDialog(final String userId) {
        // Lấy thông tin người dùng từ cơ sở dữ liệu
        ThongTinDangKy userInfo = db.getUserInfo(userId);
        if (userInfo == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo dialog sửa thông tin người dùng
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Profile");

        // Set up các trường nhập vào (EditText) để sửa thông tin
        View view = getLayoutInflater().inflate(R.layout.dialog_suahoso, null);
        final EditText edtName = view.findViewById(R.id.edtName);
        final EditText edtEmail = view.findViewById(R.id.edtEmail);
        final EditText edtPhone = view.findViewById(R.id.edtPhone);
        final EditText edtPassword = view.findViewById(R.id.edtPassword);

        // Điền dữ liệu hiện tại vào các EditText
        edtName.setText(userInfo.getHoTen());
        edtEmail.setText(userInfo.getEmail());
        edtPhone.setText(userInfo.getSdt());
        edtPassword.setText(userInfo.getPsw());

        builder.setView(view);

        // Set up nút OK để lưu thay đổi
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Lấy dữ liệu từ các EditText
            String newName = edtName.getText().toString();
            String newEmail = edtEmail.getText().toString();
            String newPhone = edtPhone.getText().toString();
            String newPassword = edtPassword.getText().toString();

            // Kiểm tra dữ liệu nhập vào
            if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(Profile.this, "Please enter complete information", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật dữ liệu trong cơ sở dữ liệu
            userInfo.setHoTen(newName);
            userInfo.setEmail(newEmail);
            userInfo.setSdt(newPhone);
            userInfo.setPsw(newPassword);
            db.updateThongTinDangKy(userInfo);

            // Cập nhật lại SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", newName);
            editor.putString("email", newEmail);
            editor.putString("sdt", newPhone);
            editor.putString("password", newPassword);
            editor.apply();

            // Hiển thị thông báo thành công
            Toast.makeText(Profile.this, "Update Successful!", Toast.LENGTH_SHORT).show();

            // Cập nhật lại các TextView trên màn hình Hồ sơ
            tvHoten2.setText(newName);
            tvemail2.setText(newEmail);
            tvSDT2.setText(newPhone);
        });

        // Set up nút Cancel để thoát dialog mà không làm thay đổi
        builder.setNegativeButton("Cancel", null);

        // Hiển thị Dialog
        builder.show();
    }

    // Hàm hiển thị Dialog xóa tài khoản
    private void showDeleteDialog(final String userId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete account")
                .setMessage("Are you sure you want to delete this account?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Xóa tài khoản trong cơ sở dữ liệu
                    int result = db.deleteUser(userId);
                    if (result > 0) {
                        // Xóa dữ liệu trong SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(Profile.this, "Delete Successful!", Toast.LENGTH_SHORT).show();

                        // Quay lại màn hình Đăng nhập (MainActivity)
                        Intent intent = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Profile.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}