package com.example.lamdoanphanmemlancuoicung.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profileuser extends AppCompatActivity {
    TextView profileName, profileEmail, profileUsername, profilePassword;
    TextView titleName, titleUsername;
    ImageButton back, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileUsername = findViewById(R.id.profileUsername);
        profilePassword = findViewById(R.id.profilePassword);

        titleName = findViewById(R.id.titleName);
        titleUsername = findViewById(R.id.titleUsername);
        back = findViewById(R.id.back);
        editButton = findViewById(R.id.editButton);

        editButton = findViewById(R.id.editButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileuser.this, manhinhuser.class);
                startActivity(intent);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileuser.this, Editprofileuser.class);
                startActivity(intent);
            }
        });

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        DocumentReference userRef = fStore.collection("User").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userEmail = documentSnapshot.getString("UserEmail");
                    String username = documentSnapshot.getString("PhoneNumber");
                    String fullName = documentSnapshot.getString("Fullname");
                    String password = documentSnapshot.getString("MatKhau");

                    // Cập nhật thông tin vào các TextView
                    profileName.setText(fullName);
                    profileEmail.setText( userEmail);
                    profileUsername.setText( username);
                    profilePassword.setText(password);

                    // Cập nhật tiêu đề
                    titleName.setText(fullName);
                    titleUsername.setText(userEmail);

                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        DocumentReference userRef = fStore.collection("User").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userEmail = documentSnapshot.getString("UserEmail");
                    String username = documentSnapshot.getString("PhoneNumber");
                    String fullName = documentSnapshot.getString("Fullname");
                    String password = documentSnapshot.getString("MatKhau");

                    // Cập nhật thông tin vào các TextView
                    profileName.setText(fullName);
                    profileEmail.setText(userEmail);
                    profileUsername.setText(username);
                    profilePassword.setText(password);

                    // Cập nhật tiêu đề
                    titleName.setText(fullName);
                    titleUsername.setText(userEmail);

                }
            }
        });
    }
}