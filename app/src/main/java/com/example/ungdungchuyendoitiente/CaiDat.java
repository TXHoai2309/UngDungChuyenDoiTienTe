package com.example.ungdungchuyendoitiente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        TextView tvChangePassword = findViewById(R.id.tvChangePassword);

        tvChangePassword.setOnClickListener(v -> showChangePasswordDialog());

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
        TextView tvThemes = findViewById(R.id.textView12);     // "Themes"
        TextView tvLanguages = findViewById(R.id.textView24);  // "Languages"
        btnGray.setOnClickListener(v ->
                        Toast.makeText(CaiDat.this, "Function under development", Toast.LENGTH_SHORT).show()
        );
        btnWhite.setOnClickListener(v ->
                Toast.makeText(CaiDat.this, "Function under development", Toast.LENGTH_SHORT).show()
        );

        tvThemes.setOnClickListener(v ->
                Toast.makeText(CaiDat.this, "Function under development", Toast.LENGTH_SHORT).show()
        );

        tvLanguages.setOnClickListener(v ->
                Toast.makeText(CaiDat.this, "Function under development", Toast.LENGTH_SHORT).show()
        );
    }
    private void showChangePasswordDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_doimatkhau);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            window.setAttributes(layoutParams);
        }

        EditText edtOld = dialog.findViewById(R.id.edtOldpassword);
        EditText edtNew = dialog.findViewById(R.id.edtNewpassword);
        EditText edtConfirm = dialog.findViewById(R.id.edtConfirmnp);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOk = dialog.findViewById(R.id.btnOk);

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Nếu là khách thì báo Toast và return luôn
        if (username.equals("Guest")) {
            Toast.makeText(this, "Please log in to use this feature!", Toast.LENGTH_SHORT).show();
            return;
        }
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnOk.setOnClickListener(v -> {
            String oldPass = edtOld.getText().toString().trim();
            String newPass = edtNew.getText().toString().trim();
            String confirmPass = edtConfirm.getText().toString().trim();
            String userId = sharedPreferences.getString("uid", ""); // Lấy uid đúng với DB
            String passwordFromPrefs = sharedPreferences.getString("matkhau", "");

            // So sánh mật khẩu cũ
            if (!oldPass.equals(passwordFromPrefs)) {
                Toast.makeText(this, "Old password is incorrect!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra mật khẩu mới với xác nhận
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật mật khẩu mới vào Database
            DataBaseHelper_DangKy db = new DataBaseHelper_DangKy(CaiDat.this);
            ThongTinDangKy user = db.getUserInfo(userId); // dùng uid, không dùng username!
            if (user == null) {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
                return;
            }
            ThongTinDangKy updatedUser = new ThongTinDangKy(
                    user.getUid(),
                    user.getHoTen(),
                    newPass,      // Mật khẩu mới
                    user.getEmail(),
                    user.getSdt()
            );
            int rows = db.updateThongTinDangKy(updatedUser);

            if (rows > 0) {
                // Cập nhật vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("matkhau", newPass);
                editor.apply();
                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Failed to update password in DB!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}