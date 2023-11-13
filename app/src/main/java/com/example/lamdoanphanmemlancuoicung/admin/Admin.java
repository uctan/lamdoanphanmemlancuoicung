package com.example.lamdoanphanmemlancuoicung.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.themxoasuasanpham;
import com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan.xemlichsudatsan;
import com.example.lamdoanphanmemlancuoicung.quanlyhoadonkhoahoc.xemlichsukhoahoc;

public class Admin extends AppCompatActivity {
    Button adqlysanpham,adqlyhoadky,adqlykhoahoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adqlykhoahoc=findViewById(R.id.adqlykhoahoc);
        adqlykhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this, xemlichsukhoahoc.class);
                startActivity(intent);
            }
        });

        adqlysanpham = findViewById(R.id.adqlysanpham);
        adqlysanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, themxoasuasanpham.class);
                startActivity(intent);
            }
        });

        adqlyhoadky=findViewById(R.id.adqlyhoadky);
        adqlyhoadky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this, xemlichsudatsan.class);
                startActivity(intent);
            }
        });
    }
}