package com.example.ungdungchuyendoitiente;

import java.util.List;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public static class Article {
        private String title;
        private String description;
        private String publishedAt;
        private String urlToImage;
        private String url;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getUrl() {
            return url;
        }
    }
}
