package com.example.newsapp;

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

import com.example.newsapp.models.ApiResponse;
import com.example.newsapp.models.Newsheadline;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener ,View.OnClickListener{
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ProgressDialog dialog;

    Button general,entertainment,business,health,sports,science,technology;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button initialization:

        general = findViewById(R.id.general);
        general.setOnClickListener(this);
        entertainment = findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);
        business = findViewById(R.id.business);
        business.setOnClickListener(this);
        health = findViewById(R.id.health);
        health.setOnClickListener(this);
        sports = findViewById(R.id.sports);
        sports.setOnClickListener(this);
        science = findViewById(R.id.science);
        science.setOnClickListener(this);
        technology = findViewById(R.id.technology);
        technology.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching Articles For you....");
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadline(listener,"general",null);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadline(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListener<ApiResponse> listener = new OnFetchDataListener<ApiResponse>() {
        @Override
        public void onFetchData(List<Newsheadline> list, String message) {
            if (list.isEmpty())
            {
                Toast.makeText(MainActivity.this, "No Data available at NewsAPI.org..", Toast.LENGTH_SHORT).show();
            }else {
                showNews(list);
                dialog.dismiss();
            }
        }
        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this,"Error !!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Newsheadline> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNewsClicked(Newsheadline headline) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("data",headline);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category  = button.getText().toString();
        dialog.setTitle("Fetching News on "+category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadline(listener,category,null);
    }
}