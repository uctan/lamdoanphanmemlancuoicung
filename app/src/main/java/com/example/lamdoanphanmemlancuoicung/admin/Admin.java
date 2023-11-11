package com.example.lamdoanphanmemlancuoicung.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.themxoasuasanpham;

public class Admin extends AppCompatActivity {
    Button adqlysanpham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adqlysanpham = findViewById(R.id.adqlysanpham);
        adqlysanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, themxoasuasanpham.class);
                startActivity(intent);
            }
        });
    }
}