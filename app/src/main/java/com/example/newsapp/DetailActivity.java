package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.models.Newsheadline;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView title,content;
    ImageView imageView;
    Newsheadline headline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        imageView = findViewById(R.id.imageView);

        headline = (Newsheadline) getIntent().getSerializableExtra("data");

        title.setText(headline.getTitle());
        content.setText(headline.getContent());
        Picasso.get().load(headline.getUrlToImage()).into(imageView);
    }
}