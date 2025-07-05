package com.example.ungdungchuyendoitiente;
public class NewsArticle {
    private String title;
    private String description;
    private String publishedAt;
    private String imageUrl;
    private String link;

    // Constructor
    public NewsArticle(String title, String description, String publishedAt, String imageUrl, String link) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
