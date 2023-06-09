package com.example.newsapplication;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsapplication.databinding.ActivityMainBinding;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.models.response.SourcesResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // binding is use to connect directly by xml.layout
    ActivityMainBinding binding;
    ProductAdaptor productAdapter;
    ArrayList<ItemsClass> classArrayList;
    private static final String API_KEY = "c75a163488ab4a06b4713098e0a11e07";
    private NewsApiClient newsApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsApiClient = new NewsApiClient(API_KEY);

        initProducts();
    }

    void initProducts() {
        classArrayList = new ArrayList<>();
        productAdapter = new ProductAdaptor(this, classArrayList);

        getRecentNews();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleView1.setLayoutManager(layoutManager);
        binding.recycleView1.setAdapter(productAdapter);
    }

    void getRecentNews() {
        EverythingRequest everythingRequest = new EverythingRequest.Builder()
                .q("trump")
                .build();

        newsApiClient.getEverything(everythingRequest, new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {

                List<Article> articles = response.getArticles();
                if (articles != null && !articles.isEmpty()) {
                    for (Article article : articles) {
                        String name = article.getTitle();
                        String description = article.getDescription();
                        String url = article.getUrl();
                        String category = article.getContent();
                        String language = article.getPublishedAt();
                        String country = article.getContent();

                        classArrayList.add(new ItemsClass(name,description,url,category,language,country));
                        // Add the retrieved data to the list
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productAdapter.notifyDataSetChanged(); // Notify the adapter of the data change
                        }
                    });
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.e("NewsApiClient", "Failed to get recent news: " + throwable.getMessage());
            }
        });
    TopHeadlinesRequest topHeadlinesRequest = new TopHeadlinesRequest.Builder()
                .q("bitcoin")
                .language("en")
                .build();
        newsApiClient.getTopHeadlines(topHeadlinesRequest, new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {
                List<Article> articles = response.getArticles();
                if (articles != null && !articles.isEmpty()) {
                    Article firstArticle = articles.get(0);
                    String title = firstArticle.getTitle();
                    // Use the article title as needed
                    Log.d("NewsApiClient", "First article title: " + title);
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.e("NewsApiClient", "Failed to get top headlines: " + throwable.getMessage());
            }
        });

        SourcesRequest sourcesRequest = new SourcesRequest.Builder()
                .language("en")
                .country("us")
                .build();
        newsApiClient.getSources(sourcesRequest, new NewsApiClient.SourcesCallback() {
            @Override
            public void onSuccess(SourcesResponse response) {
                List<Source> sources = response.getSources();
                if (sources != null && !sources.isEmpty()) {
                    Source firstSource = sources.get(0);
                    String name = firstSource.getName();
                    // Use the source name as needed
                    Log.d("NewsApiClient", "First source name: " + name);
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.e("NewsApiClient", "Failed to get sources: " + throwable.getMessage());
            }
        });
    }
}