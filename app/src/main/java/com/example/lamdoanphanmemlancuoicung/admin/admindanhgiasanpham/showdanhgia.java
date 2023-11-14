package com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class showdanhgia extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterReviews adapter;
    ImageButton closecmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdanhgia);

        recyclerView = findViewById(R.id.recyclerViewdanhgia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterReviews(new ArrayList<>(), FirebaseFirestore.getInstance()); // Khởi tạo adapter
        recyclerView.setAdapter(adapter);

        closecmt = findViewById(R.id.closecmt);
        closecmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showdanhgia.this, manhinhuser.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore.getInstance().collection("Reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String fullName = (String) document.get("fullname");
                                float rating = Float.parseFloat(document.get("rating").toString());
                                String review = (String) document.get("message");

                                ReviewItem reviewItem = new ReviewItem(rating, review, fullName);
                                adapter.addReview(reviewItem);
                            }
                            adapter.notifyDataSetChanged(); // Thêm dòng này để thông báo cập nhật dữ liệu
                        } else {
                            // Xử lý khi có lỗi
                        }
                    }
                });
    }

}