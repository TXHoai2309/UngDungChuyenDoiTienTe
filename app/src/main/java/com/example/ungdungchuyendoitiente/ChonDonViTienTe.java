package com.example.ungdungchuyendoitiente;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChonDonViTienTe extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ThongTinCacQuocGiaADapter ChonDonViadapter;
    private List<ThongTinCacQuocGia> ThongTinCacQuocGiaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chon_don_vi_tien_te);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.ChuyenDoiRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ThongTinCacQuocGiaList = new ArrayList<>();
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Việt Nam", "VND", R.drawable.icon_vietnam));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Mỹ", "USD", R.drawable.icon_usa));

        ChonDonViadapter = new ThongTinCacQuocGiaADapter(this, ThongTinCacQuocGiaList); // Đổi thứ tự tham số
        recyclerView.setAdapter(ChonDonViadapter);
    }
}