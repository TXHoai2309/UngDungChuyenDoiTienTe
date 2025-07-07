package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BieuDo extends AppCompatActivity {
    private TextView txtThoiGian, txtDate;
    private CandleStickChart candleStickChart;
    private String DulieuBieuDo;
    private ImageView imgBack;

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

        candleStickChart = findViewById(R.id.candleStickChart);

        readCSVAndDisplayChart();

        txtThoiGian = findViewById(R.id.txtThoiGian);
        txtDate = findViewById(R.id.txtDate);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        txtThoiGian.setText(time);
        txtDate.setText(date);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(BieuDo.this, Convert.class);
            finish();
        });
    }

    private void readCSVAndDisplayChart() {
        try {
            File file = new File(getFilesDir(), "FileDuLieu.csv");
            if (!file.exists()) {
                Log.e("CSV Error", "FileDuLieu.csv không tồn tại trong internal storage.");
                return;
            }

            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] row;
            ArrayList<CandleEntry> entries = new ArrayList<>();
            ArrayList<String> xLabels = new ArrayList<>();
            int count = 0;

            DulieuBieuDo = getIntent().getStringExtra("DulieuBieuDo");
            Log.d("CSV", "Đọc dữ liệu cho slug: " + DulieuBieuDo);

            csvReader.readNext(); // Bỏ dòng tiêu đề

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
                            xLabels.add(row[1]); // Cột chứa ngày/giờ
                        } catch (NumberFormatException e) {
                            Log.e("CSV Error", "Error parsing row: " + row[0], e);
                        }
                    }
                    count++;
                }
            }

            if (!entries.isEmpty()) {
                CandleDataSet dataSet = new CandleDataSet(entries, DulieuBieuDo);

                // Nến TradingView Style
                dataSet.setDecreasingColor(android.graphics.Color.RED);
                dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
                dataSet.setIncreasingColor(android.graphics.Color.GREEN);
                dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
                dataSet.setNeutralColor(android.graphics.Color.LTGRAY);
                dataSet.setShadowColor(android.graphics.Color.DKGRAY);
                dataSet.setShadowColorSameAsCandle(true);
                dataSet.setDrawValues(false);

                // Trục X
                XAxis xAxis = candleStickChart.getXAxis();
                xAxis.setDrawGridLines(true);
                xAxis.setGridColor(android.graphics.Color.DKGRAY);
                xAxis.setGridLineWidth(0.5f);
                xAxis.setDrawAxisLine(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setDrawLabels(true);
                xAxis.setLabelRotationAngle(-45f);
                xAxis.setTextColor(android.graphics.Color.LTGRAY);
                xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xLabels));


                // Trục Y
                YAxis leftAxis = candleStickChart.getAxisLeft();
                leftAxis.setDrawGridLines(true);
                leftAxis.setGridColor(android.graphics.Color.DKGRAY);
                leftAxis.setGridLineWidth(0.5f);
                leftAxis.setTextColor(android.graphics.Color.LTGRAY);

                candleStickChart.getAxisRight().setEnabled(false);

                // Cài đặt chart
                candleStickChart.setBackgroundColor(android.graphics.Color.parseColor("#383636"));
                candleStickChart.getDescription().setEnabled(false);
                candleStickChart.setPinchZoom(true);
                candleStickChart.setScaleEnabled(true);
                candleStickChart.setDragEnabled(true);
                candleStickChart.setDrawBorders(false);
                candleStickChart.animateX(800);
                candleStickChart.moveViewToX(entries.size() - candleStickChart.getVisibleXRange());

                CandleData candleData = new CandleData(dataSet);
                candleStickChart.setData(candleData);

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
