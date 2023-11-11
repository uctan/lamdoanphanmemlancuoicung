package com.example.lamdoanphanmemlancuoicung.admin.adminyeuthich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.Dataclass;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class favorites extends AppCompatActivity {
    private RecyclerView favoriteRecyclerView;
    private List<Dataclass> favoriteList;
    private FavoriteAdapter favoriteAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageButton closefvr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteRecyclerView = findViewById(R.id.recyclerViewFavourite);
        favoriteList = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(this, favoriteList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        favoriteRecyclerView.setLayoutManager(layoutManager);
        favoriteRecyclerView.setAdapter(favoriteAdapter);

        loadFavoriteListFromFirestore();

        closefvr = findViewById(R.id.closefvr);
        closefvr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(favorites.this, manhinhuser.class);
                startActivity(intent);
            }
        });
    }

    private void loadFavoriteListFromFirestore() {
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            favoriteList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Dataclass dataclass = document.toObject(Dataclass.class);
                                if (dataclass.isFavorite()) {
                                    favoriteList.add(dataclass);
                                }
                            }
                            favoriteAdapter.notifyDataSetChanged();
                        } else {
                            // Handle the error
                        }
                    }
                });
    }
}