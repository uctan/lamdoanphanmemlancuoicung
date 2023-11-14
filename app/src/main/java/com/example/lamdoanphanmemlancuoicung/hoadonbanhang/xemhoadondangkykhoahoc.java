package com.example.lamdoanphanmemlancuoicung.hoadonbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.ItemSpacingDecoration;
import com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon.hoadonclass;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.trangdangky;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class xemhoadondangkykhoahoc extends AppCompatActivity {
    ImageButton closehoadonkhoahoc;
    RecyclerView recyclerViewhoadondangkykhoahoc;
    khoahocAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemhoadondangkykhoahoc);
        closehoadonkhoahoc = findViewById(R.id.closehoadonkhoahoc);
        recyclerViewhoadondangkykhoahoc = findViewById(R.id.recyclerViewhoadondangkykhoahoc);

        closehoadonkhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xemhoadondangkykhoahoc.this, manhinhuser.class);
                startActivity(intent);
            }
        });

        List<hoadonclass> hoadonclassList = new ArrayList<>();
        adapter = new khoahocAdapter(this, hoadonclassList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(xemhoadondangkykhoahoc.this, 1);
        recyclerViewhoadondangkykhoahoc.setLayoutManager(gridLayoutManager);
        recyclerViewhoadondangkykhoahoc.addItemDecoration(new ItemSpacingDecoration(16));
        recyclerViewhoadondangkykhoahoc.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference hoadonrRef = db.collection("Hoadon");
        hoadonrRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String username = document.getString("fullname");
                        String khoahoc = document.getString("khoahoc");
                        int thanhtoan = document.getLong("thanhtoan").intValue();

                        hoadonclass hoadonclass = new hoadonclass(0, username, "", khoahoc, thanhtoan);
                        hoadonclassList.add(hoadonclass);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }
}