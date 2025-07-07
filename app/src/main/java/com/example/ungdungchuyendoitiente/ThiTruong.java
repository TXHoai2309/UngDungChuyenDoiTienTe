package com.example.ungdungchuyendoitiente;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThiTruong extends AppCompatActivity {
    TextView txtThoiGian, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thi_truong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtThoiGian = findViewById(R.id.txtThoiGian);
        txtDate = findViewById(R.id.txtDate);

        // Lấy thowif gian hiện tại
        Calendar calendar = Calendar.getInstance();

        // Định dạng thời gian
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        String time = timeFormat.format(calendar.getTime());

        // Định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        // Cập nhật vào các TextView
        txtThoiGian.setText(time);
        txtDate.setText(date);
    }
}