package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.util.Pair;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Retrofit imports


public class Convert extends AppCompatActivity implements View.OnClickListener{

    private TextView tvmoney1, tvmoney2, tvTigia;
    private boolean isTopSelected = true;
    private String numberTop = null;
    private String numberBottom = null;
    private boolean operator = false;
    private String status = null;
    private double firtnumberTop = 0, lastnumberTop = 0;
    private double firtnumberBottom = 0, lastnumberBottom = 0;
    private ImageView imgvietnam, imgusa, imgResetPrice;
    private ImageButton btnchange;


    private static final int REQUEST_CODE_SELECT_TOP = 1001;
    private static final int REQUEST_CODE_SELECT_BOTTOM = 1002;

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



        // Set default currencies and update rate on startup
        tvmoney1.setText("USD");
        tvmoney2.setText("VND");
        loadPrice("USD", "VND");


        // Sự kiện cho nút làm mới tỷ giá
        imgResetPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
                long lastReffreshTime = sharedPreferences.getLong("lastRefreshTime", 0);
                // Kiểm tra nếu đã 2p kể từ lần làm mới cuối cùng(2p = 120000ms)
                if(currentTime - lastReffreshTime >= 120000){
                    String fromCurrency = tvmoney1.getText().toString();
                    String toCurrency = tvmoney2.getText().toString();
                    loadPrice(fromCurrency, toCurrency);
                    Toast.makeText(getApplicationContext(),"Updated successfully! " , Toast.LENGTH_SHORT).show();


                    // Cập nhật thời gian làm mới
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("lastRefreshTime", currentTime);
                    editor.apply();
                }else{
                    long timeLeft =(120000-(currentTime-lastReffreshTime))/1000; // Thời gian còn lại tính bằng giây
                    Toast.makeText(getApplicationContext(),"Vui lòng đợi " + timeLeft + " giây trước khi làm mới", Toast.LENGTH_SHORT).show();
                }

            }
        });




        // Khai báo, setting và mặc định cho menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_convert);

        // Sự kiện cho nút đổi
        btnchange.setOnClickListener(v -> {
            // Hoán đổi ImageView
            Drawable tempImage = imgvietnam.getDrawable();
            imgvietnam.setImageDrawable(imgusa.getDrawable());
            imgusa.setImageDrawable(tempImage);

            // Hoán đổi TextView
            String tempText = tvmoney1.getText().toString();
            tvmoney1.setText(tvmoney2.getText().toString());
            tvmoney2.setText(tempText);
        });
        imgvietnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Convert.this, ChonDonViTienTe.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_TOP);
            }
        });
        // cmt
        imgusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Convert.this, ChonDonViTienTe.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_BOTTOM);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                // Chuyển sang màn CaiDat
                Intent intent = new Intent(Convert.this, CaiDat.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_convert) {
                // Đang ở trang Convert, không cần làm gì
                return true;
            } else if (item.getItemId() == R.id.bottom_check) {
                return true;
            }
            return false;
        });
        // Hết phần menu

        // Chọn dòng nhập
        tvmoney1.setOnClickListener(v -> {
            isTopSelected = true;
            tvmoney1.setBackgroundResource(R.drawable.selected_border);
            tvmoney2.setBackgroundResource(android.R.color.transparent);
        });
        tvmoney2.setOnClickListener(v -> {
            isTopSelected = false;
            tvmoney2.setBackgroundResource(R.drawable.selected_border);
            tvmoney1.setBackgroundResource(android.R.color.transparent);
        });

        // Khởi tạo trạng thái đầu
        tvmoney1.setBackgroundResource(R.drawable.selected_border);
        tvmoney2.setBackgroundResource(android.R.color.transparent);

        // Gán sự kiện cho các button
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

    public Pair<String, String> getDataIntentChonDonViTienTe() {

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String maTienTe = intent.getStringExtra("maTienTe");  // Nhận mã tiền tệ từ Intent
        String tenQuocGia = intent.getStringExtra("tenQuocGia"); // Nhận tên quốc gia từ Intent

        // Trả về Pair chứa maTienTe và tenQuocGia
        return new Pair<>(maTienTe, tenQuocGia);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            int imgResId = data.getIntExtra("imgResId", -1);
            String maTienTe = data.getStringExtra("maTienTe");
            if (imgResId != -1 && maTienTe != null) {
                if (requestCode == REQUEST_CODE_SELECT_TOP) {
                    imgvietnam.setImageResource(imgResId);
                    tvmoney1.setText(maTienTe);

                } else if (requestCode == REQUEST_CODE_SELECT_BOTTOM) {
                    imgusa.setImageResource(imgResId);
                    tvmoney2.setText(maTienTe);
                }
                // Cập nhật tỷ giá khi chọn đơn vị tiền tệ mới
                String fromCurrency = tvmoney1.getText().toString();
                String toCurrency = tvmoney2.getText().toString();
                loadPrice(fromCurrency, toCurrency);
            }
        }
    }

    // Get all rates with base USD, then calculate fromCurrency -> toCurrency
    public void loadPrice(String fromCurrency, String toCurrency) {
        final String from = fromCurrency.trim().toUpperCase();
        final String to = toCurrency.trim().toUpperCase();
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
                    tvTigia.setText(String.format("1 %s = %.4f %s", from, rate, to));
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


    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Chọn dòng làm việc
        String number = isTopSelected ? numberTop : numberBottom;
        double firtnumber = isTopSelected ? firtnumberTop : firtnumberBottom;
        double lastnumber = isTopSelected ? lastnumberTop : lastnumberBottom;

        // --- Xử lý nhấn số ---
        if (id == R.id.btn0) number = addNumber(number, "0");
        else if (id == R.id.btn1) number = addNumber(number, "1");
        else if (id == R.id.btn2) number = addNumber(number, "2");
        else if (id == R.id.btn3) number = addNumber(number, "3");
        else if (id == R.id.btn4) number = addNumber(number, "4");
        else if (id == R.id.btn5) number = addNumber(number, "5");
        else if (id == R.id.btn6) number = addNumber(number, "6");
        else if (id == R.id.btn7) number = addNumber(number, "7");
        else if (id == R.id.btn8) number = addNumber(number, "8");
        else if (id == R.id.btn9) number = addNumber(number, "9");
        else if (id == R.id.btndot) number = addDot(number);

            // --- Xóa ---
        else if (id == R.id.btnc) {
            number = null;
            firtnumber = 0;
            lastnumber = 0;
            status = null;
            operator = false;
        }
        // --- Xóa 1 ký tự ---
        else if (id == R.id.btne && number != null && number.length() > 0) {
            number = number.substring(0, number.length() - 1);
        }

        // --- Cộng ---
        else if (id == R.id.btnadd) {
            firtnumber = getNumberOrDefault(number, firtnumber);
            number = null;
            status = "plus";
            operator = true;
        }

        // --- Trừ ---
        else if (id == R.id.btnsub) {
            firtnumber = getNumberOrDefault(number, firtnumber);
            number = null;
            status = "minus";
            operator = true;
        }

        // --- Nhân ---
        else if (id == R.id.btnmul) {
            firtnumber = (firtnumber == 0) ? 1 : firtnumber;
            firtnumber = getNumberOrDefault(number, firtnumber);
            number = null;
            status = "multi";
            operator = true;
        }

        // --- Chia ---
        else if (id == R.id.btndiv) {
            firtnumber = getNumberOrDefault(number, firtnumber);
            number = null;
            status = "div";
            operator = true;
        }

        // --- Bằng ---
        else if (id == R.id.btnequal) {
            if (operator && number != null) {
                lastnumber = Double.parseDouble(number);
                firtnumber = calculate(firtnumber, lastnumber, status);
                number = String.valueOf(firtnumber);
                operator = false;
            }
        }

        // --- Cập nhật lại biến ---
        if (isTopSelected) {
            numberTop = number;
            firtnumberTop = firtnumber;
            lastnumberTop = lastnumber;
            tvmoney1.setText(numberTop != null ? numberTop : "0");
        } else {
            numberBottom = number;
            firtnumberBottom = firtnumber;
            lastnumberBottom = lastnumber;
            tvmoney2.setText(numberBottom != null ? numberBottom : "0");
        }
    }

    private String addNumber(String number, String value) {
        if (number == null || number.equals("0")) return value;
        else return number + value;
    }
    private String addDot(String number) {
        if (number == null) return "0,";
        if (!number.contains(",")) return number + ",";
        return number;
    }
    private double getNumberOrDefault(String number, double defaultValue) {
        if (number == null || number.isEmpty()) return defaultValue;
        return Double.parseDouble(number);
    }
    private double calculate(double a, double b, String op) {
        if ("plus".equals(op)) return a + b;
        if ("minus".equals(op)) return a - b;
        if ("multi".equals(op)) return a * b;
        if ("div".equals(op)) return b != 0 ? a / b : 0;
        return b;
    }



}
