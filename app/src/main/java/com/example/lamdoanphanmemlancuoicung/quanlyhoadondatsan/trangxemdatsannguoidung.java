package com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.Booking;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class trangxemdatsannguoidung extends AppCompatActivity {

    View trangxemdatsanclose;
    RecyclerView recyclerViewhoadondatsan;
    lichsuAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangxemdatsannguoidung);
        trangxemdatsanclose = findViewById(R.id.trangxemdatsanclose);
        trangxemdatsanclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trangxemdatsannguoidung.this, manhinhuser.class);
                startActivity(intent);
            }
        });

        recyclerViewhoadondatsan = findViewById(R.id.recyclerViewhoadondatsan);
        recyclerViewhoadondatsan.setLayoutManager(new LinearLayoutManager(this));
        List<Booking> bookingList = new ArrayList<>();
        adapter = new lichsuAdapter(this,bookingList);
        recyclerViewhoadondatsan.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference bookingRef = db.collection("bookings");
        bookingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tennguoithue = document.getString("tennguoithue");
                        String fieldName = document.getString("fieldName");
                        int price = document.getLong("price").intValue();
                        String ngaythuesan = document.getString("date");
                        String tensanthue = document.getString("tensanthue");

                        Booking booking = new Booking(fieldName, ngaythuesan, null, null, price, tensanthue, tennguoithue);
                        bookingList.add(booking);
                    }
                    adapter.notifyDataSetChanged();

                   
                }
            }
        });


    }
}