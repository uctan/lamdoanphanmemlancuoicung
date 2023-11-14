package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham.vietcomment;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.DetailActivity;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.thongtindangky;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.trangdangky;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class thongtinticket extends AppCompatActivity {
    TextView tensanthuene, ngaythuesanne, tennguoithuene, idtensanbongthue, giothuesan, gioketthucthuesan, idthanhtoansan;

    ImageButton saveticket,vectorttquaylaiticket;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinticket);
        tennguoithuene = findViewById(R.id.tennguoithuene);
        tensanthuene = findViewById(R.id.tensanthuene);
        ngaythuesanne = findViewById(R.id.ngaythuesanne);
        idtensanbongthue = findViewById(R.id.idtensanbongthue);
        giothuesan = findViewById(R.id.giothuesan);
        gioketthucthuesan = findViewById(R.id.gioketthucthuesan);
        idthanhtoansan = findViewById(R.id.idthanhtoansan);
        saveticket = findViewById(R.id.savetickett);
        vectorttquaylaiticket = findViewById(R.id.vectorttquaylaiticket);
        vectorttquaylaiticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongtinticket.this, giaodienthuesan.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        String tenSan = intent.getStringExtra("tenSan");
        String selectedDate = intent.getStringExtra("selectedDate");
        String gioBatDau = intent.getStringExtra("gioBatDau");
        String gioKetThuc = intent.getStringExtra("gioKetThuc");
        int gia = intent.getIntExtra("gia", 0);
        String name = intent.getStringExtra("name");

        tensanthuene.setText(name);
        ngaythuesanne.setText(selectedDate);
        idtensanbongthue.setText(tenSan);
        giothuesan.setText(gioBatDau);
        gioketthucthuesan.setText(gioKetThuc);
        idthanhtoansan.setText(String.valueOf(gia));


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        DocumentReference userRef = fStore.collection("User").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("Fullname");
                    // Cập nhật thông tin vào các TextView
                    tennguoithuene.setText(fullName);

                }
            }
        });
        saveticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSanThuene = tensanthuene.getText().toString();
                String ngayThueSanNe = ngaythuesanne.getText().toString();
                String gioThueSan = giothuesan.getText().toString();
                String gioKetThucThueSan = gioketthucthuesan.getText().toString();
                String tenNguoiThueNe = tennguoithuene.getText().toString();
                String idTenSanBongThue = idtensanbongthue.getText().toString();
                int idThanhToanSan = Integer.parseInt(idthanhtoansan.getText().toString());

                // Chuyển đổi chuỗi thời gian thành đối tượng LocalTime
                LocalTime gioBatDau = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    gioBatDau = LocalTime.parse(gioThueSan, DateTimeFormatter.ofPattern("HH:mm"));
                }
                LocalTime gioKetThuc = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    gioKetThuc = LocalTime.parse(gioKetThucThueSan, DateTimeFormatter.ofPattern("HH:mm"));
                }

                // Tạo một đối tượng Booking từ dữ liệu nhập
                Booking booking = new Booking(idTenSanBongThue, ngayThueSanNe, gioBatDau, gioKetThuc, idThanhToanSan, tenSanThuene, tenNguoiThueNe);

                // Lưu đối tượng Booking vào Firestore
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                fStore.collection("bookings")
                        .add(booking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Xử lý khi lưu vào Firestore thành công
                                Toast.makeText(thongtinticket.this, "Lưu thông tin đặt sân thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(thongtinticket.this, vietcomment.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xử lý khi lưu vào Firestore thất bại
                                Toast.makeText(thongtinticket.this, "Lưu thông tin đặt sân thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}