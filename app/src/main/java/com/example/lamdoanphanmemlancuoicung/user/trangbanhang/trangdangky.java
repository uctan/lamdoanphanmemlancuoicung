package com.example.lamdoanphanmemlancuoicung.user.trangbanhang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.Dataclass;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.MyAdapter;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.UploadActivity;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.example.lamdoanphanmemlancuoicung.user.profileuser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class trangdangky extends AppCompatActivity {
    ImageButton carrt,vectortrangbanhang;
    RecyclerView recyclerView;
    List<Dataclass> dataList;
    List<Boolean> isFavoriteList; // Danh sách trạng thái yêu thích của các sản phẩm
    CollectionReference productsCollection;
    MyAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangdangky);

        carrt = findViewById(R.id.fab);
        vectortrangbanhang = findViewById(R.id.vectortrangbanhang);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        dataList = new ArrayList<>();
        isFavoriteList = new ArrayList<>();


        isFavoriteList = new ArrayList<>(); // Khởi tạo danh sách trạng thái yêu thích



        adapter = new MyAdapter(trangdangky.this, dataList, isFavoriteList); // Truyền danh sách yêu thích vào adapter


        GridLayoutManager gridLayoutManager = new GridLayoutManager(trangdangky.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adapter);

        productsCollection = FirebaseFirestore.getInstance().collection("products");

        AlertDialog.Builder builder = new AlertDialog.Builder(trangdangky.this);
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
                isFavoriteList.clear();
                for (DocumentSnapshot document : value) {
                    Dataclass dataclass = document.toObject(Dataclass.class);
                    dataList.add(dataclass);
                    isFavoriteList.add(dataclass.isFavorite());
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        carrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(trangdangky.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        vectortrangbanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(trangdangky.this, manhinhuser.class);
                startActivity(intent);
            }
        });
    }

    public void searchList(String text) {
        ArrayList<Dataclass> searchList = new ArrayList<>();
        for (Dataclass dataClass : dataList) {
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}