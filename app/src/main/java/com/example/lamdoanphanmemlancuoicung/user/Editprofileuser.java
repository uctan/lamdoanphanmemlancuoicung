package com.example.lamdoanphanmemlancuoicung.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editprofileuser extends AppCompatActivity {
    EditText editName, editEmail, editUsername, editPassword;
    ImageButton saveButton;
    String currentName, currentEmail, currentUsername, currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileuser);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin được nhập từ các trường EditText
                String newName = editName.getText().toString();
                String newEmail = editEmail.getText().toString();
                String newUsername = editUsername.getText().toString();
                String newPassword = editPassword.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();

                // Cập nhật dữ liệu lên Firestore
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                DocumentReference userRef = fStore.collection("User").document(userId);
                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put("Fullname", newName);
                userUpdates.put("UserEmail", newEmail);
                userUpdates.put("PhoneNumber", newUsername);
                userUpdates.put("MatKhau", newPassword);
                userRef.update(userUpdates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Editprofileuser.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                finish(); // Đóng hoạt động hiện tại (Màn hình chỉnh sửa thông tin)
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Editprofileuser.this, "Lỗi khi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
            DocumentReference userRef = fStore.collection("User").document(userId);

            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        currentName = documentSnapshot.getString("Fullname");
                        currentEmail = documentSnapshot.getString("UserEmail");
                        currentUsername = documentSnapshot.getString("PhoneNumber");
                        currentPassword = documentSnapshot.getString("MatKhau");

                        // Hiển thị thông tin lên EditText
                        editName.setText(currentName);
                        editEmail.setText(currentEmail);
                        editUsername.setText(currentUsername);
                        editPassword.setText(currentPassword);
                    }
                }
            });
        }
    }
}