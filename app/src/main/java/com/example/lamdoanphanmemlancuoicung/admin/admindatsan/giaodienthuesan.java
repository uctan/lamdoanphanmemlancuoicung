package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.trangdangky;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class giaodienthuesan extends AppCompatActivity {
    RecyclerView recyclerView;
    DatsanAdapter datsanAdapter;

    private List<FootballField> footballFields;
    CollectionReference footballCollection;
    ImageButton vectortrangthuesan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodienthuesan);

        // Kết nối RecyclerView
        recyclerView = findViewById(R.id.recyclerViewthuesan);
        footballFields = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(giaodienthuesan.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        footballCollection = FirebaseFirestore.getInstance().collection("football_fields");

        vectortrangthuesan = findViewById(R.id.vectortrangthuesan);
        vectortrangthuesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(giaodienthuesan.this, manhinhuser.class);
                startActivity(intent);
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(giaodienthuesan.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        datsanAdapter = new DatsanAdapter(footballFields);
        recyclerView.setAdapter(datsanAdapter);

        // Lấy dữ liệu từ Firestore và cập nhật RecyclerView thông qua adapter
        footballCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<FootballField> newFootballFields = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String name = documentSnapshot.getString("name");
                    String imageResourceId = documentSnapshot.getString("imageResourceId");
                    FootballField footballField = new FootballField(name, imageResourceId);
                    newFootballFields.add(footballField);
                }
                datsanAdapter.updateFootballFields(newFootballFields);
                dialog.dismiss(); // Tắt dialog sau khi đã lấy dữ liệu
            }
        });


    }

}




