package com.example.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.databinding.NewsLayoutBinding;

import java.util.ArrayList;
public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.ProductViewHolder>{
    Context context;
    ArrayList<ItemsClass> classArrayList;
    public ProductAdaptor(Context context, ArrayList<ItemsClass> products) {
        this.context = context;
        this.classArrayList = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ItemsClass item = classArrayList.get(position);

        holder.binding.nameTextView.setText(item.getName());
        holder.binding.descriptionTextView.setText(item.getDescription());
        holder.binding.urlTextView.setText(item.getUrl());
        holder.binding.languageTextView.setText(item.getLanguage());
        holder.binding.countryTextView.setText(item.getCountry());
    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        NewsLayoutBinding binding;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NewsLayoutBinding.bind(itemView);
        }
    }
}
