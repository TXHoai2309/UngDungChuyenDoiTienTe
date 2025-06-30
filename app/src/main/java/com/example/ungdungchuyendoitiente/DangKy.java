package com.example.ungdungchuyendoitiente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangKy extends AppCompatActivity {
    private EditText edtUid,edtHoten,edtPsw,edtEmail,edtSdt;
    private Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUid = findViewById(R.id.edtuid2);
        edtHoten = findViewById(R.id.edthoten);
        edtPsw = findViewById(R.id.edtpsw2);
        edtEmail = findViewById(R.id.edtemail);
        edtSdt = findViewById(R.id.edtsdt);
        btnDangKy = findViewById(R.id.btndangky);

    }
    // cmt

    // binh lai 3
}