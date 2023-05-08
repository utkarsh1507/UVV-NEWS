package com.mrj.quicknews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mrj.quicknews.models.NewsApiResponse;
import com.mrj.quicknews.models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    SearchView searchView;
    Button b1, b2, b3, b4, b5, b6, b7;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                progressDialog.setTitle("Fetching news articles of "+query);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",query);

                return true; 
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching news articles.");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);

    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(MainActivity.this, "No data found :( ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }else {
                showNews(list);
                progressDialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error!! Occurred!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new NewsAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", headlines));
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;

        String category = button.getText().toString();

        progressDialog.setTitle("Fetching news articles of "+category);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null);
    }
}