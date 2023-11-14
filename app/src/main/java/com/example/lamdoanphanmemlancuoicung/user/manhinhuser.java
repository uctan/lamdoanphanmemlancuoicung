package com.example.lamdoanphanmemlancuoicung.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham.showdanhgia;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.giaodienthuesan;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.UploadActivity;
import com.example.lamdoanphanmemlancuoicung.admin.adminyeuthich.favorites;
import com.example.lamdoanphanmemlancuoicung.dangnhap;
import com.example.lamdoanphanmemlancuoicung.hoadonbanhang.xemhoadondangkykhoahoc;
import com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan.trangxemdatsannguoidung;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.trangdangky;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class manhinhuser extends AppCompatActivity {
    ImageButton theloai1,yeuthich,theloai2,lichsu;
    ImageButton canhan;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhuser);


        lichsu = findViewById(R.id.lichsu);
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhuser.this, xemhoadondangkykhoahoc.class);
                startActivity(intent);
            }
        });
        theloai2 = findViewById(R.id.theloai2);
        theloai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhuser.this, giaodienthuesan.class);
                startActivity(intent);
            }
        });
        //lam thanh cong cu
        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                View headerView = navigationView.getHeaderView(0);
                TextView nameUser = headerView.findViewById(R.id.username);
                TextView emailUser = headerView.findViewById(R.id.usernameid);

                // Đặt giá trị tên người dùng và tên người dùng ID vào các TextView
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    DocumentReference userRef = fStore.collection("User").document(userId);
                    userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String fullName = documentSnapshot.getString("Fullname");
                                String userEmail = documentSnapshot.getString("UserEmail");

                                // Cập nhật thông tin lên Toolbar
                                nameUser.setText(fullName);
                                emailUser.setText(userEmail);
                            }
                        }
                    });
                }

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);


                if (id == R.id.nav_cn) {
                    Toast.makeText(manhinhuser.this, "Bạn đã vào trang cá nhân", Toast.LENGTH_SHORT).show();



                } else if (id == R.id.nav_home) {
                    Toast.makeText(manhinhuser.this, "Bạn đã vào trang cá nhân", Toast.LENGTH_SHORT).show();
                    // Không nên chuyển hướng đến trang chủ của chính activity trangchu nếu đã ở đó rồi
                    // Để lại màn hình hiện tại không thay đổi
                } else if (id == R.id.nav_lichsudatsan) {
                    Toast.makeText(manhinhuser.this, "Bạn đã vào trang xem đặt sân", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manhinhuser.this, trangxemdatsannguoidung.class);
                    startActivity(intent);

                }
               else if (id == R.id.nav_yt) {
                    Toast.makeText(manhinhuser.this, "Bạn đã vào trang cá nhân", Toast.LENGTH_SHORT).show();

                }
                else  if (id == R.id.nav_lichsu){
                    Toast.makeText(manhinhuser.this, "Bạn đã vào trang cá nhân", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(manhinhuser.this, showdanhgia.class);
                    startActivity(intent);

                } else if (id == R.id.nav_dangxuat) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), dangnhap.class));
                    finish();

                }

                return true;
            }
        });
        canhan = findViewById(R.id.canhan);
        canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhuser.this, profileuser.class);

                startActivity(intent);
            }
        });


        theloai1 = findViewById(R.id.theloai1);
        theloai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhuser.this, trangdangky.class);
                startActivity(intent);
            }
        });
        yeuthich = findViewById(R.id.yeuthich);
        yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manhinhuser.this, favorites.class);
                startActivity(intent);
            }
        });
    }
}