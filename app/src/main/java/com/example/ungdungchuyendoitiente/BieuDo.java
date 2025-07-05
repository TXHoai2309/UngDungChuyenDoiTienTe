package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BieuDo extends AppCompatActivity {

    private CandleStickChart candleStickChart; // Biểu đồ nến
    private String DulieuBieuDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bieu_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // khởi tạo CandleStickChart
        candleStickChart = findViewById(R.id.candleStickChart);

        // Đọc dữ liệu từ file CSV và hiển thị biểu đồ
        readCSVAndDisplayChart();

        // Menu dưới
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        //bottomNavigationView.setSelectedItemId(R.id.bottom_news);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                Intent intent = new Intent(BieuDo.this, CaiDat.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_convert) {
                Intent intent = new Intent(BieuDo.this, Convert.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_news) {
                return true;
            }
            return false;
        });
    }

    private void readCSVAndDisplayChart() {
        try {
            // Đọc file nội bộ (không dùng assets)
            File file = new File(getFilesDir(), "FileDuLieu.csv");
            if (!file.exists()) {
                Log.e("CSV Error", "FileDuLieu.csv không tồn tại trong internal storage.");
                return;
            }

            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] row;
            ArrayList<CandleEntry> entries = new ArrayList<>();
            int count = 0;

            DulieuBieuDo = getIntent().getStringExtra("DulieuBieuDo");
            Log.d("CSV", "Đọc dữ liệu cho slug: " + DulieuBieuDo);

            // Bỏ dòng tiêu đề
            csvReader.readNext();

            while ((row = csvReader.readNext()) != null) {
                if (row.length > 5) {
                    String slug = row[0];

                    if (slug.equals(DulieuBieuDo)) {
                        try {
                            float open = parseFloatSafe(row[2]);
                            float high = parseFloatSafe(row[3]);
                            float low = parseFloatSafe(row[4]);
                            float close = parseFloatSafe(row[5]);

                            entries.add(new CandleEntry(entries.size(), high, low, open, close));
                        } catch (NumberFormatException e) {
                            Log.e("CSV Error", "Error parsing row: " + row[0], e);
                        }
                    }
                    count++;
                }
            }

            if (!entries.isEmpty()) {
                CandleDataSet dataSet = new CandleDataSet(entries, DulieuBieuDo);

                // Nến giảm (đỏ)
                dataSet.setDecreasingColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                dataSet.setDecreasingPaintStyle(Paint.Style.FILL);

                // Nến tăng (xanh)
                dataSet.setIncreasingColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                dataSet.setIncreasingPaintStyle(Paint.Style.FILL);

                // Nến không đổi (cam)
                dataSet.setNeutralColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));

                dataSet.setDrawValues(false); // Không hiện giá trị số

                // Tùy chỉnh trục X
                XAxis xAxis = candleStickChart.getXAxis();
                xAxis.setDrawLabels(false);

                // Tùy chỉnh màu
                candleStickChart.getDescription().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getXAxis().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getAxisLeft().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getAxisRight().setTextColor(ContextCompat.getColor(this, android.R.color.white));

                // Đưa dữ liệu lên biểu đồ
                CandleData candleData = new CandleData(dataSet);
                candleStickChart.setData(candleData);

                candleStickChart.setDragEnabled(true);
                candleStickChart.setScaleEnabled(true);
                candleStickChart.setVisibleXRangeMaximum(35);

                candleStickChart.invalidate();
            } else {
                Log.e("CSV Error", "Không có dữ liệu để hiển thị cho: " + DulieuBieuDo);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            Log.e("CSV Error", "Lỗi đọc file CSV", e);
        }
    }

    private float parseFloatSafe(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            Log.e("CSV Error", "Invalid number format: " + value, e);
            return 0f;
        }
    }
}
