package com.example.ungdungchuyendoitiente;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsArticle> newsArticles;

    public NewsAdapter(List<NewsArticle> newsArticles) {
        this.newsArticles = newsArticles;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_trongnuoc, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsArticle article = newsArticles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.publishedAt.setText(article.getPublishedAt());

        // Log URL ảnh để kiểm tra
        Log.d("Image URL", article.getImageUrl());

        Glide.with(holder.itemView.getContext())
                .load(article.getImageUrl())
                .error(R.drawable.ic_error)  // Nếu có lỗi, hiển thị ảnh lỗi
                .into(holder.thumbnail);
        holder.itemView.setOnClickListener(v -> {
            String link = article.getLink();
            if (link != null && !link.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, publishedAt;
        ImageView thumbnail;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            publishedAt = itemView.findViewById(R.id.tvPublishedAt);
            thumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}
