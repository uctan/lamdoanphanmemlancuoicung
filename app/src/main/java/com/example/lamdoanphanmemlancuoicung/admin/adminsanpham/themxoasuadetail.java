package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class themxoasuadetail extends AppCompatActivity {
    TextView detailDescthem, detailTitlethem, detailLangthem;
    ImageView detailImagethem;
    FloatingActionButton deleteButton,editButton;
    ImageButton closeadminquanly;
    String key = "";
    String imageUrl = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themxoasuadetail);
        detailImagethem = findViewById(R.id.detailImagethem);
        closeadminquanly = findViewById(R.id.closeadminquanly);

        detailDescthem = findViewById(R.id.detailDescthem);
        detailTitlethem = findViewById(R.id.detailTitlethem);
        detailLangthem = findViewById(R.id.detailLangthem);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDescthem.setText(bundle.getString("Description"));
            detailTitlethem.setText(bundle.getString("Title"));
            detailLangthem.setText(String.valueOf(bundle.getInt("Giaca")));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImagethem);
        }
        closeadminquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(themxoasuadetail.this, themxoasuasanpham.class);
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference productsCollection = db.collection("products");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        productsCollection.document(key).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(themxoasuadetail.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), themxoasuasanpham.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(themxoasuadetail.this, "Lỗi khi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(themxoasuadetail.this, "Lỗi khi xóa ảnh", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(themxoasuadetail.this,UpdateActivity.class)
                        .putExtra("Title",detailDescthem.getText().toString())
                        .putExtra("Description",detailTitlethem.getText().toString())
                        .putExtra("Giaca",detailLangthem.getText().toString())
                        .putExtra("Image",imageUrl)
                        .putExtra("Key",key);
                startActivity(intent);



            }
        });
    }
}