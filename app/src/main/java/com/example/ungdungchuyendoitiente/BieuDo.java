package com.example.ungdungchuyendoitiente;

import android.content.Context;
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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BieuDo extends AppCompatActivity {


    private CandleStickChart candleStickChart ; // Biểu đồ nến

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

        // khởi tạo Canlestickchart
        candleStickChart = findViewById(R.id.candleStickChart);
        // Đọc dữ liệu từ file CSV và hiển thị biểu đồ
        readCSVAndDisplayChart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_check);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                // Chuyển sang màn CaiDat
                Intent intent = new Intent(BieuDo.this, CaiDat.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_check) {
                return true;
            } else if (item.getItemId() == R.id.bottom_convert){
                Intent intent = new Intent(BieuDo.this, Convert.class);
                startActivity(intent);
                return true;
            }

            else if (item.getItemId() == R.id.bottom_check) {
                return true;
            }
            return false;
        });
        // Hết phần menu
    }
    private void readCSVAndDisplayChart() {
        try {
            InputStream inputStream = getAssets().open("FileDuLieu.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(inputStreamReader);
            String[] row;
            ArrayList<CandleEntry> entries = new ArrayList<>();
            int count = 0;  // Chỉ số đếm để lọc lấy mỗi 10 dòng 1

            while ((row = csvReader.readNext()) != null) {
                if (row.length > 5) {
                    String slug = row[0];  // Cặp tiền tệ (ví dụ: GBP/EGP)

                    // Chỉ lấy dữ liệu cho cặp USD/VND
                    if (slug.equals("USD/VND") && count % 10 == 0) {
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

            if (entries.size() > 0) {
                // Tạo biểu đồ cho tỷ giá USD/VND
                CandleDataSet dataSet = new CandleDataSet(entries, "USD/VND");

                // Nến giảm (red)
                dataSet.setDecreasingColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                dataSet.setDecreasingPaintStyle(Paint.Style.FILL);

                // Nến tăng (green)
                dataSet.setIncreasingColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                dataSet.setIncreasingPaintStyle(Paint.Style.FILL);

                // Nến không đổi (gray)
                dataSet.setNeutralColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));


                // Màu chữ hiển thị giá trị trên nến (black)
                dataSet.setValueTextColor(ContextCompat.getColor(this, android.R.color.black));

                // Tắt việc hiển thị giá trị trên các nến
                dataSet.setDrawValues(false);  // Tắt số trên các nến

                // Tùy chỉnh lại các trục (x, y) cho đẹp mắt
                dataSet.setValueTextSize(10f);
                dataSet.setFormLineWidth(1f);
                dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                dataSet.setFormSize(15f);

                // Lấy đối tượng trục X
                XAxis xAxis = candleStickChart.getXAxis();

                    // Tắt việc hiển thị nhãn (số) trên trục X
                xAxis.setDrawLabels(false);


                dataSet.setValueTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark));
                candleStickChart.getDescription().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getXAxis().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getAxisLeft().setTextColor(ContextCompat.getColor(this, android.R.color.white));
                candleStickChart.getAxisRight().setTextColor(ContextCompat.getColor(this, android.R.color.white));



                // Tạo và hiển thị dữ liệu cho biểu đồ
                CandleData candleData = new CandleData(dataSet);
                candleStickChart.setData(candleData);

                // Cho phép zoom và kéo
                candleStickChart.setDragEnabled(true);  // Cho phép kéo biểu đồ
                candleStickChart.setScaleEnabled(true); // Cho phép zoom
                candleStickChart.setVisibleXRangeMaximum(35);  // Hiển thị tối đa 10 ngày trên màn hình

                candleStickChart.invalidate();  // Làm mới biểu đồ
            } else {
                Log.e("CSV Error", "No data available for USD/VND.");
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
            Log.e("CSV Error", "Error reading CSV file", e);
        }
    }
    // Hàm phụ để xử lý chuyển đổi chuỗi thành số, tránh lỗi NumberFormatException
    private float parseFloatSafe(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            Log.e("CSV Error", "Invalid number format for value: " + value, e);
            return 0f;  // Nếu không thể chuyển đổi, trả về giá trị mặc định là 0
        }
    }
}