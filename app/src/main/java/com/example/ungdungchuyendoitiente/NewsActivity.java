package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsArticle> newsArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadDomesticNews();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_news);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_setting) {
                Intent intent = new Intent(NewsActivity.this, CaiDat.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.bottom_news) {
                return true;
            } else if (item.getItemId() == R.id.bottom_convert) {
                Intent intent = new Intent(NewsActivity.this, Convert.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Set up Toolbar menu click
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.bottom_trongnuoc) {
                loadDomesticNews();
                return true;
            } else if (item.getItemId() == R.id.bottom_quocte) {
                loadApiNews();
                return true;
            }
            return false;
        });
    }


    private void loadDomesticNews() {
        new Thread(() -> {
            List<NewsArticle> allNews = new ArrayList<>();
            allNews.addAll(NguonTinTuc.fetchCafeF());
            allNews.addAll(NguonTinTuc.fetchVietNamBiz());

            // Không giới hạn số lượng tin trong nước!
            runOnUiThread(() -> {
                newsAdapter = new NewsAdapter(allNews);
                recyclerView.setAdapter(newsAdapter);
            });
        }).start();
    }

    private void loadApiNews() {
        new Thread(() -> {
            List<NewsArticle> apiNews = NguonTinTuc.apiNews_en();

            // Giới hạn còn 20 tin
            List<NewsArticle> limitedNews = apiNews.size() > 20 ? apiNews.subList(0, 20) : apiNews;

            runOnUiThread(() -> {
                newsAdapter = new NewsAdapter(limitedNews);
                recyclerView.setAdapter(newsAdapter);
            });
        }).start();
    }
}
