package com.example.lamdoanphanmemlancuoicung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class dangkygmail extends AppCompatActivity {

    TextView registerBtngmail;
    EditText registerNamegmail,registerEmailgmail,registerPhonegmail;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangkygmail);
        registerNamegmail = findViewById(R.id.registerNamegmail);
        registerEmailgmail = findViewById(R.id.registerEmailgmail);
        registerPhonegmail = findViewById(R.id.registerPhonegmail);
        registerBtngmail = findViewById(R.id.registerBtngmail);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        registerBtngmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registerNamegmail.getText().toString();
                String email = registerEmailgmail.getText().toString();
                String phone = registerPhonegmail.getText().toString();

                // Kiểm tra nếu thông tin không trống
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone)) {

                    // Kiểm tra người dùng đã đăng nhập chưa
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        // Tạo một Map chứa thông tin người dùng
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("Fullname", name);
                        userInfo.put("UserEmail", email);
                        userInfo.put("PhoneNumber", phone);

                        // Lưu thông tin vào Firestore
                        fStore.collection("User").document(currentUser.getUid())
                                .set(userInfo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(dangkygmail.this, "Thông tin đã được lưu vào Firestore", Toast.LENGTH_SHORT).show();
                                        // (Optional) Nếu cần, thực hiện các bước tiếp theo sau khi lưu thông tin
                                        Intent intent = new Intent(dangkygmail.this,dangnhap.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(dangkygmail.this, "Lỗi khi lưu thông tin vào Firestore", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(dangkygmail.this, "Người dùng chưa đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(dangkygmail.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}