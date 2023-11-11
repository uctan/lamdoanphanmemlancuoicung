package com.example.lamdoanphanmemlancuoicung.user.trangbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;

public class DetailActivity extends AppCompatActivity {
    TextView detailDesc, detailTitle, detailLang;
    ImageView detailImage;
    ImageButton savedt, close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailLang = findViewById(R.id.detailLang);
        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        close = findViewById(R.id.close);
        savedt = findViewById(R.id.savedt);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLang.setText(String.valueOf(bundle.getInt("Giaca"))); // Chuyển thành int
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, trangdangky.class);
                startActivity(intent);
            }
        });

        savedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, thongtindangky.class);
                intent.putExtra("Title", detailTitle.getText().toString());
                intent.putExtra("Giaca", detailLang.getText().toString());
                startActivity(intent);
            }
        });


    }
}