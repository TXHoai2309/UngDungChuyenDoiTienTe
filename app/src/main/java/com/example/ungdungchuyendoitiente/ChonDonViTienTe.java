package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private ThongTinCacQuocGiaADapter ChonDonViadapter;
    public static final List<ThongTinCacQuocGia> ThongTinCacQuocGiaList = new ArrayList<>(); ;
    // Static block to initialize the list
    static {
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Việt Nam", "VND", R.drawable.icon_vietnam));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Mỹ", "USD", R.drawable.icon_usa));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Trung Quốc", "CNY", R.drawable.ic_china));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Cuba", "CUP", R.drawable.ic_cuba));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Ấn Độ", "INR", R.drawable.ic_india));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Indonesia", "IDR", R.drawable.ic_indonesia));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Nhật Bản", "JPY", R.drawable.ic_japam));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Hàn Quốc", "KRW", R.drawable.ic_korea));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Lào", "LAK", R.drawable.ic_laos));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Malaysia", "MYR", R.drawable.ic_malaysia));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Myanmar", "MMK", R.drawable.ic_myanmar));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Philippines", "PHP", R.drawable.ic_philippins));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Singapore", "SGD", R.drawable.ic_singapor));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Đài Loan", "TWD", R.drawable.ic_taiwan));
        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Đông Timor", "USD", R.drawable.ic_timo_lest));
    }
    private EditText edtTimKiem;
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
        imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.ChuyenDoiRecyclerView);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChonDonViTienTe.this, Convert.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Việt Nam", "VND", R.drawable.icon_vietnam));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Mỹ", "USD", R.drawable.icon_usa));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Trung Quốc", "CNY", R.drawable.ic_china));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Cuba", "CUP", R.drawable.ic_cuba));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Ấn Độ", "INR", R.drawable.ic_india));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Indonesia", "IDR", R.drawable.ic_indonesia));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Nhật Bản", "JPY", R.drawable.ic_japam)); // Chú ý tên file ic_japam
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Hàn Quốc", "KRW", R.drawable.ic_korea));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Lào", "LAK", R.drawable.ic_laos));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Malaysia", "MYR", R.drawable.ic_malaysia));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Myanmar", "MMK", R.drawable.ic_myanmar));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Philippines", "PHP", R.drawable.ic_philippins));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Singapore", "SGD", R.drawable.ic_singapor));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Đài Loan", "TWD", R.drawable.ic_taiwan));
//        ThongTinCacQuocGiaList.add(new ThongTinCacQuocGia("Đông Timor", "USD", R.drawable.ic_timo_lest));
//

        ChonDonViadapter = new ThongTinCacQuocGiaADapter(ThongTinCacQuocGiaList, this);
        recyclerView.setAdapter(ChonDonViadapter);

        // Xử lý khi nhấn vào cờ quốc gia
        ChonDonViadapter.setOnItemClickListener(new ThongTinCacQuocGiaADapter.OnItemClickListener() {
            @Override
            public void onItemClick(ThongTinCacQuocGia item) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("imgResId", item.getHinhQuocGia());
                resultIntent.putExtra("maTienTe", item.getMaTienTe());
                resultIntent.putExtra("tenQuocGia", item.getTenQuocGia());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        edtTimKiem = findViewById(R.id.edtTimKiem);
        edtTimKiem.setOnEditorActionListener((v, actionId, event) -> {;
            String query = edtTimKiem.getText().toString().trim().toLowerCase();
            List<ThongTinCacQuocGia> filteredList = new ArrayList<>();
            for (ThongTinCacQuocGia item : ThongTinCacQuocGiaList) {
                if (item.getTenQuocGia().toLowerCase().contains(query) || item.getMaTienTe().toLowerCase().contains(query)) {
                    Toast.makeText(this, "Searching: " + query, Toast.LENGTH_SHORT).show();
                    filteredList.add(item);
                }
            }
            ChonDonViadapter.updateList(filteredList);
            return true;
        });
    }


}
