package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private ImageView imgvietnam, imgusa;
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


        // Khởi tạo Retrofit và API Client
        ExchangeRateApi api = ApiClient_Price.getClient().create(ExchangeRateApi.class);
        // Gọi API để lấy tỷ giá USD->VND
        Call<ExchangeRateResponse> call = api.getExchangeRates("USD");
        call.enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ExchangeRateResponse exchangeRateResponse = response.body();
                    Double vndRate = exchangeRateResponse.getConversionRates().get("VND");
                    if(vndRate!=null){
                        tvTigia.setText("1 USD = " + vndRate + " VND");
                    } else {
                        tvTigia.setText("Không tìm thấy tỷ giá VND");
                    }
                }
            }

            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                // to do something
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
            }
        }
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
