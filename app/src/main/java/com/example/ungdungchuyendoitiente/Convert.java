package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Convert extends AppCompatActivity implements View.OnClickListener {

    private TextView tvmoney1, tvmoney2, tvTigia, tvapi;
    private boolean isTopSelected = true;
    private String numberTop = "";
    private String numberBottom = "";
    private ImageView imgvietnam, imgusa, imgResetPrice;
    private ImageButton btnchange;

    private static final int REQUEST_CODE_SELECT_TOP = 1001;
    private static final int REQUEST_CODE_SELECT_BOTTOM = 1002;

    private String tenQuocGiaTop = "Mỹ";
    private String tenQuocGiaBottom = "Việt Nam";


    private String currentOperator = "";
    private double operandTop = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_convert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tvmoney1 = findViewById(R.id.tvmoney1);
        tvmoney2 = findViewById(R.id.tvmoney2);
        btnchange = findViewById(R.id.btnchange);
        imgvietnam = findViewById(R.id.imgvietnam);
        imgusa = findViewById(R.id.imgusa);
        tvTigia = findViewById(R.id.tvapi);
        imgResetPrice = findViewById(R.id.imgrefresh);

        loadPrice(tenQuocGiaTop, tenQuocGiaBottom);

        imgResetPrice.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
            long lastRefreshTime = sharedPreferences.getLong("lastRefreshTime", 0);

            // CHỈ TRUYỀN TÊN QUỐC GIA VÀO
            loadPrice(tenQuocGiaTop, tenQuocGiaBottom);

            Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("lastRefreshTime", currentTime);
            editor.apply();

            // Dòng này dùng đúng mã tiền tệ
            String fromCurrency = getCurrencyCodeByCountry(tenQuocGiaTop);
            String toCurrency = getCurrencyCodeByCountry(tenQuocGiaBottom);
            updateDataFromApi(fromCurrency, toCurrency);
            CsvUtils.sortCsvFile(this, fromCurrency + "/" + toCurrency);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_convert);

        btnchange.setOnClickListener(v -> {
            Drawable tempImage = imgvietnam.getDrawable();
            imgvietnam.setImageDrawable(imgusa.getDrawable());
            imgusa.setImageDrawable(tempImage);

            // Đổi giá trị tiền và reset về 0
            String tempText = "0";  // Giá trị reset về 0
            tvmoney1.setText(tempText);
            tvmoney2.setText(tempText);

            String tempCountry = tenQuocGiaTop;
            tenQuocGiaTop = tenQuocGiaBottom;
            tenQuocGiaBottom = tempCountry;

            // Đặt lại số tiền về mặc định
            numberTop = "";  // Reset numberTop về trống
            numberBottom = "";  // Reset numberBottom về trống
        });

        imgvietnam.setOnClickListener(v -> {
            Intent intent = new Intent(Convert.this, ChonDonViTienTe.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_TOP);
        });

        imgusa.setOnClickListener(v -> {
            Intent intent = new Intent(Convert.this, ChonDonViTienTe.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_BOTTOM);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                Intent intent = new Intent(Convert.this, CaiDat.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_convert) {
                return true;
            } else if (item.getItemId() == R.id.bottom_news) {
                Intent intent = new Intent(Convert.this, NewsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        tvTigia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromCurrency = getCurrencyCodeByCountry(tenQuocGiaTop);
                String toCurrency = getCurrencyCodeByCountry(tenQuocGiaBottom);
                Intent intent = new Intent(Convert.this, BieuDo.class);
                intent.putExtra("DulieuBieuDo", fromCurrency + "/" + toCurrency);
                startActivity(intent);
            }
        });


        tvmoney1.setOnClickListener(v -> {
            isTopSelected = true;
            tvmoney1.setBackgroundResource(R.drawable.selected_border);
        });

        tvmoney1.setBackgroundResource(R.drawable.selected_border);

        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnadd, R.id.btnsub, R.id.btnmul, R.id.btndiv,
                R.id.btndot, R.id.btnc, R.id.btne, R.id.btnequal
        };
        for (int id : buttonIds) {
            View btn = findViewById(id);
            if (btn != null) btn.setOnClickListener(this);
        }
    }

    private String getCurrencyCodeByCountry(String tenQuocGia) {
        for (ThongTinCacQuocGia item : ChonDonViTienTe.ThongTinCacQuocGiaList) {
            if (item.getTenQuocGia().equals(tenQuocGia)) {
                return item.getMaTienTe();
            }
        }
        return "USD";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            int imgResId = data.getIntExtra("imgResId", -1);
            String maTienTe = data.getStringExtra("maTienTe");
            String tenQuocGia = data.getStringExtra("tenQuocGia");

            if (imgResId != -1 && maTienTe != null) {
                if (requestCode == REQUEST_CODE_SELECT_TOP) {
                    imgvietnam.setImageResource(imgResId);
                    tenQuocGiaTop = tenQuocGia;
                    convertCurrency(isTopSelected);
                } else if (requestCode == REQUEST_CODE_SELECT_BOTTOM) {
                    imgusa.setImageResource(imgResId);
                    tenQuocGiaBottom = tenQuocGia;
                    convertCurrency(isTopSelected);
                }
                loadPrice(tenQuocGiaTop, tenQuocGiaBottom);

                convertCurrency(isTopSelected);
            }
        }
    }

    public void loadPrice(String tenQuocGiaFrom, String tenQuocGiaTo) {
        String from = getCurrencyCodeByCountry(tenQuocGiaFrom);
        String to = getCurrencyCodeByCountry(tenQuocGiaTo);
        ExchangeRateApi api = ApiClient_Price.getClient().create(ExchangeRateApi.class);
        Call<ExchangeRateResponse> call = api.getExchangeRates("USD");
        call.enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ExchangeRateResponse rates = response.body();
                    Double usdToFrom = rates.getConversionRates().get(from);
                    Double usdToTo = rates.getConversionRates().get(to);
                    if (usdToFrom == null || usdToTo == null) {
                        tvTigia.setText("No rate for " + from + " or " + to);
                        return;
                    }
                    double rate = usdToTo / usdToFrom;
                    //String formattedRate = formatResult(rate);
                    tvTigia.setText(String.format("1 %s = %s %s", from, rate, to));

                    convertCurrency(isTopSelected);
                } else {
                    tvTigia.setText("Error fetching rates");
                }
            }
            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                tvTigia.setText("API error");
            }
        });
    }

    private void convertCurrency(boolean fromTop) {
        //String fromCurrency = getCurrencyCodeByCountry(tenQuocGiaTop);
        //String toCurrency = getCurrencyCodeByCountry(tenQuocGiaBottom);
        String rateText = tvTigia.getText().toString();

        if (rateText.isEmpty() || rateText.contains("No rate") || rateText.contains("Error")) {
            Toast.makeText(this, "Please update the exchange rate first", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] parts = rateText.split("=");
        if (parts.length == 2) {
            String rateStr = parts[1].replaceAll("[^\\d.]", "").trim();
            try {
                double rate = Double.parseDouble(rateStr);
                if (fromTop) {
                    double amount = parseNumber(numberTop);
                    double result = amount * rate;
                    numberBottom = formatResult(result);
                    tvmoney2.setText(numberBottom);
                } else {
                    double amount = parseNumber(numberBottom);
                    double result = amount / rate;
                    numberTop = formatResult(result);
                    tvmoney1.setText(numberTop);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid rate format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid rate format", Toast.LENGTH_SHORT).show();
        }
    }

    private double parseNumber(String number) {
        if (number == null || number.isEmpty()) return 0;
        return Double.parseDouble(number.replace(",", "."));
    }



    public String formatResult(double num) {
        // Định dạng số với phân cách hàng nghìn bằng dấu .
        DecimalFormat formatter = new DecimalFormat("###.###");
        return formatter.format(num);  // Trả về số đã định dạng
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (isTopSelected) {
            if (id == R.id.btnc) {
                numberTop = "";
                tvmoney1.setText("0");
                numberBottom = "";
                tvmoney2.setText("0");
                currentOperator = "";
                isOperatorPressed = false;
            } else if (id == R.id.btne) {
                if (!numberTop.isEmpty()) {
                    numberTop = numberTop.substring(0, numberTop.length() - 1);
                    tvmoney1.setText(numberTop.isEmpty() ? "0" : numberTop);
                }
            }else if (id == R.id.btndot) {
                String[] parts = numberTop.split("[" + "\\+\\-\\*/" + "]");
                String currentOperand = parts.length > 1 ? parts[1].trim() : parts[0];
                if (!currentOperand.contains(",")) {
                    numberTop += currentOperand.isEmpty() ? "0," : ",";
                    tvmoney1.setText(numberTop);
                }
            } else if (id == R.id.btnadd || id == R.id.btnsub || id == R.id.btnmul || id == R.id.btndiv) {
                if (!numberTop.isEmpty() && !isOperatorPressed) {
                    operandTop = parseNumber(numberTop);
                    isOperatorPressed = true;

                    if (id == R.id.btnadd) {
                        currentOperator = "+";
                    } else if (id == R.id.btnsub) {
                        currentOperator = "-";
                    } else if (id == R.id.btnmul) {
                        currentOperator = "*";
                    } else if (id == R.id.btndiv) {
                        currentOperator = "/";
                    }

                    numberTop += " " + currentOperator + " ";
                    tvmoney1.setText(numberTop);
                }
            } else if (id == R.id.btnequal) {
                if (isOperatorPressed) {
                    String[] parts = numberTop.split("[" + "\\+\\-\\*/" + "]");
                    if (parts.length == 2) {
                        double secondOperand = parseNumber(parts[1].trim());
                        double result = 0;
                        switch (currentOperator) {
                            case "+": result = operandTop + secondOperand; break;
                            case "-": result = operandTop - secondOperand; break;
                            case "*": result = operandTop * secondOperand; break;
                            case "/": result = secondOperand != 0 ? operandTop / secondOperand : 0; break;
                        }
                        numberTop = formatResult(result);
                        tvmoney1.setText(numberTop);
                        isOperatorPressed = false;
                        currentOperator = "";
                        convertCurrency(true);
                    }
                } else {
                    convertCurrency(true);
                }
            } else if (id >= R.id.btn0 && id <= R.id.btn9) {
                String value = String.valueOf(id - R.id.btn0);
                numberTop += value;
                tvmoney1.setText(numberTop);
                if (!isOperatorPressed) convertCurrency(true);
            }
        }
    }


    public void updateDataFromApi(String fromSymbol, String toSymbol) {
        // Tạo URL
        String apiUrl = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol="
                + fromSymbol + "&to_symbol=" + toSymbol
                + "&outputsize=full&apikey=B15AUJQ9234KXN1O";

        new Thread(() -> {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();

                JSONObject obj = new JSONObject(result.toString());
                JSONObject timeSeries = obj.getJSONObject("Time Series FX (Daily)");

                String slug = fromSymbol + "/" + toSymbol;
                String currency = toSymbol;
                Set<String> existingDates = CsvUtils.getExistingDates(this, slug);

                int addedCount = 0;
                // 1. Lấy toàn bộ ngày
                List<String> dateList = new ArrayList<>();
                Iterator<String> it = timeSeries.keys();
                while (it.hasNext()) {
                    dateList.add(it.next());
                }

// 2. Sắp xếp ngày tăng dần (cũ -> mới)
                Collections.sort(dateList);

// 3. Duyệt danh sách đã sắp
                for (String date : dateList) {
                    if (!existingDates.contains(date)) {
                        JSONObject dayData = timeSeries.getJSONObject(date);
                        String open = dayData.getString("1. open");
                        String high = dayData.getString("2. high");
                        String low = dayData.getString("3. low");
                        String close = dayData.getString("4. close");

                        String dateForCsv = CsvUtils.convertToMdyyyy(date);
                        String lineCsv = slug + "," + dateForCsv + "," + open + "," + high + "," + low + "," + close + "," + currency;
                        CsvUtils.appendLine(this, lineCsv);
                        addedCount++;
                    }
                }


                int finalAddedCount = addedCount;
                runOnUiThread(() -> Toast.makeText(this, "Đã thêm " + finalAddedCount + " bản ghi mới.", Toast.LENGTH_SHORT).show());

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Lỗi tải dữ liệu.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

}