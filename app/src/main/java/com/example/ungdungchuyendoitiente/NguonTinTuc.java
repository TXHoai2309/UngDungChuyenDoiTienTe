package com.example.ungdungchuyendoitiente;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class NguonTinTuc {
    private static final String API_KEY = "d0aeb270d25b4018ad66b6e1b73f9ff0";

    public static List<NewsArticle> fetchVietNamBiz() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://vietnambiz.vn/tai-chinh.htm").get();
            Elements newsList = doc.select("div.item");
            for (Element news : newsList) {
                // Link bài viết
                Element aTag = news.selectFirst("div.image > a");
                String link = aTag != null ? aTag.attr("href") : "";
                if (!link.startsWith("http") && !link.isEmpty()) link = "https://vietnambiz.vn" + link;

                // Mô tả
                String desc = aTag != null ? Jsoup.parse(aTag.attr("title")).text() : "";

                // Tiêu đề
                Element h3 = news.selectFirst("div.content > h3.title");
                String title = h3 != null ? Jsoup.parse(h3.html()).text() : "";

                // Ngày giờ
                Element timeDiv = news.selectFirst("div.content > div.time");
                String pubDate = timeDiv != null ? Jsoup.parse(timeDiv.html()).text() : "";

                // Ảnh đại diện
                Element img = news.selectFirst("img.img120x80");
                String imgUrl = (img != null) ? img.attr("src") : "";

                // **Chỉ add nếu có title và link và imgUrl (đủ dữ liệu cần thiết)**
                if (!title.isEmpty() && !link.isEmpty() && !imgUrl.isEmpty()) {
                    articles.add(new NewsArticle(title, desc, pubDate, imgUrl, link));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
    public static List<NewsArticle> fetchCafeF() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://cafef.vn/thi-truong-chung-khoan.chn")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36")
                    .timeout(10000)
                    .get();

            Elements newsList = doc.select("div.tlitem");
            for (Element news : newsList) {
                // Link + Ảnh + Tiêu đề
                Element aTag = news.selectFirst("a.avatar.img-resize");
                String link = aTag != null ? aTag.attr("href") : "";
                if (!link.startsWith("http") && !link.isEmpty()) link = "https://cafef.vn" + link;

                Element img = aTag != null ? aTag.selectFirst("img") : null;
                String imgUrl = img != null ? img.attr("src") : "";

                // Tiêu đề (bài mới nhất lấy từ thẻ <a>, tin dưới lấy từ <a> hoặc <h3>)
                String title = aTag != null ? aTag.attr("title") : "";

                // Thời gian
                Element timeSpan = news.selectFirst("span.time");
                String pubDate = timeSpan != null ? timeSpan.text() : "";

                // Mô tả
                String desc = aTag != null ? Jsoup.parse(aTag.attr("title")).text() : "";

                // Chỉ add nếu có đủ dữ liệu
                if (!title.isEmpty() && !link.isEmpty() && !imgUrl.isEmpty()) {
                    articles.add(new NewsArticle(title, desc, pubDate, imgUrl, link));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
    public static List<NewsArticle> apiNews_en() {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            NewsApiService apiService = ApiClient_News_en.getClient().create(NewsApiService.class);
            Response<NewsApiResponse> response = apiService.getNews("economy", API_KEY).execute();

            if (response.isSuccessful() && response.body() != null) {
                List<NewsApiResponse.Article> apiArticles = response.body().getArticles();
                for (NewsApiResponse.Article art : apiArticles) {
                    articles.add(new NewsArticle(
                            art.getTitle(),
                            art.getDescription(),
                            art.getPublishedAt(),
                            art.getUrlToImage(),
                            art.getUrl()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}
