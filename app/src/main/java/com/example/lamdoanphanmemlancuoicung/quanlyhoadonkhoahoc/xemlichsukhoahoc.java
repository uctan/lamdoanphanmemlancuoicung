package com.example.lamdoanphanmemlancuoicung.quanlyhoadonkhoahoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.Admin;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.Booking;
import com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon.hoadonclass;
import com.example.lamdoanphanmemlancuoicung.hoadonbanhang.khoahocAdapter;
import com.example.lamdoanphanmemlancuoicung.hoadonbanhang.xemhoadondangkykhoahoc;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class xemlichsukhoahoc extends AppCompatActivity {

    ImageButton closelichsukhoahoc;
    RecyclerView recyclerViewlichsukhoahoc;
    lichsukhoahocAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemlichsukhoahoc);
        closelichsukhoahoc = findViewById(R.id.closelichsukhoahoc);
        recyclerViewlichsukhoahoc = findViewById(R.id.recyclerViewlichsukhoahoc);

        closelichsukhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xemlichsukhoahoc.this, Admin.class);
                startActivity(intent);
            }
        });
        recyclerViewlichsukhoahoc.setLayoutManager(new LinearLayoutManager(this));
        List<hoadonclass> hoadonclassList = new ArrayList<>();
        adapter = new lichsukhoahocAdapter(this, hoadonclassList);
        recyclerViewlichsukhoahoc.setAdapter(adapter);

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

                    calculateAndDisplayTotal(hoadonclassList);
                } else {
                }
            }
        });
    }

    private void calculateAndDisplayTotal(List<hoadonclass> hoadonclassList) {
        double totalAmount = 0.0;


        for (hoadonclass hoadonclasss : hoadonclassList) {
            totalAmount += hoadonclasss.getThanhtoan();
        }


        TextView totalAmountkhoahoc = findViewById(R.id.totalAmountkhoahoc);
        totalAmountkhoahoc.setText(String.valueOf(totalAmount));
    }
}