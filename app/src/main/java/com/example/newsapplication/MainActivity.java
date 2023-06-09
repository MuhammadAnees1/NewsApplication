package com.example.newsapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // binding is use to connect directly by xml.layout
    ActivityMainBinding binding;
    ProductAdaptor productAdaptor;
//    make array for store api data in it
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycleView1.setLayoutManager(layoutManager);
        binding.recycleView1.setAdapter(productAdaptor);
    }


    void getRecentNews() {
        RequestQueue queue = Volley.newRequestQueue(this);
// make array object
        List<ItemsClass> sources = new ArrayList<>();

//        StringRequest used to send HTTP requests and receive string responses.
        StringRequest request = new StringRequest(Request.Method.GET,Constants. API_BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

//                create a JSONObject from the response string.This line of code is important because the response received from the API is in the form of a JSON string.
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("ok")) {
//                    make JSON array for getting data in the api array

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }
}