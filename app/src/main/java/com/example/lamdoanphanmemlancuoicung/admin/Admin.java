package com.example.lamdoanphanmemlancuoicung.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.Uploadbooking;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.themxoasuasanpham;
import com.example.lamdoanphanmemlancuoicung.dangnhap;
import com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan.xemlichsudatsan;
import com.example.lamdoanphanmemlancuoicung.quanlyhoadonkhoahoc.xemlichsukhoahoc;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {
    View adqlysanpham,adqlyhoadky,adqlykhoahoc,adquanlysan;
    ImageView dangxuatnguoiban;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        adquanlysan = findViewById(R.id.adquanlysan);
        adquanlysan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, Uploadbooking.class);
                startActivity(intent);
            }
        });

        dangxuatnguoiban = findViewById(R.id.dangxuatnguoiban);
        dangxuatnguoiban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), dangnhap.class));
                finish();
            }
        });

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