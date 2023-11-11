package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.trangdangky;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class themxoasuasanpham extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Dataclass> dataList;
    CollectionReference productsCollection;
    themxoasuaadapter themxoasuaadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themxoasuasanpham);

        recyclerView = findViewById(R.id.themxoasuarecyclerView);
        dataList = new ArrayList<>();
        themxoasuaadapter = new themxoasuaadapter(themxoasuasanpham.this,dataList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(themxoasuasanpham.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(themxoasuaadapter);

        productsCollection = FirebaseFirestore.getInstance().collection("products");

        AlertDialog.Builder builder = new AlertDialog.Builder(themxoasuasanpham.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        productsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Xử lý lỗi
                    return;
                }

                dataList.clear();

                // Loop qua các documents và thêm vào danh sách
                for (DocumentSnapshot document : value.getDocuments()) {
                    Dataclass data = document.toObject(Dataclass.class);
                    data.setKey(document.getId());
                    dataList.add(data);
                }
                dialog.dismiss();
                // Cập nhật Adapter
                themxoasuaadapter.notifyDataSetChanged();

                // Ẩn ProgressBar khi dữ liệu đã tải

            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(themxoasuasanpham.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }
}