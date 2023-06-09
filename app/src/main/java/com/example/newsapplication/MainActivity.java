package com.example.newsapplication;

import static com.example.newsapplication.Constants.API_BASE_URL;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapplication.databinding.ActivityMainBinding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ProductAdaptor productAdaptor;
    ArrayList<ItemsClass> classArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initProducts();
    }
    void initProducts() {
        classArrayList = new ArrayList<>();
        productAdaptor = new ProductAdaptor(this, classArrayList);

        getRecentNews();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.recycleView1.setLayoutManager(layoutManager);
        binding.recycleView1.setAdapter(productAdaptor);
    }

    void getRecentNews() {
        RequestQueue queue = Volley.newRequestQueue(this);

        List<ItemsClass> sources = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, API_BASE_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);

                if (object.getString("status").equals("ok")) {

                    JSONArray sourcesArray = object.getJSONArray("sources");

                    for (int i = 0; i < sourcesArray.length(); i++) {
                        JSONObject sourceObj = sourcesArray.getJSONObject(i);

                        ItemsClass source = new ItemsClass(
                                sourceObj.getString("id"),
                                sourceObj.getString("name"),
                                sourceObj.getString("description"),
                                sourceObj.getString("url"),
                                sourceObj.getString("category"),
                                sourceObj.getString("language"),
                                sourceObj.getString("country")
                        );
                        sources.add(source);
                    }
                    productAdaptor.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        queue.add(request);
    }
}