package com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class vietcomment extends AppCompatActivity {
    ImageButton cmt;
    RatingBar ratingStarts;
    EditText messagedata;
    float myRating = 0;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vietcomment);

        cmt = findViewById(R.id.cmt);
        ratingStarts = findViewById(R.id.ratingBar);
        messagedata = findViewById(R.id.messagedata);
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference userRef = firestore.collection("User").document(userId);

            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("Fullname");

                        cmt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String message = messagedata.getText().toString();

                                if (message.isEmpty()) {
                                    messagedata.setError("Bạn nên điền vào");
                                } else {
                                    // Lưu đánh giá
                                    myRating = ratingStarts.getRating();
                                    ReviewItem reviewItem = new ReviewItem(myRating, message, fullName);

                                    saveDataToFirestore(reviewItem);

                                    // Chuyển sang trang showdanhgia và gửi dữ liệu đánh giá
                                    Intent intent = new Intent(vietcomment.this, showdanhgia.class);
                                    intent.putExtra("rating", myRating);
                                    intent.putExtra("review", message);
                                    intent.putExtra("fullName", fullName);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        } else {
            // Người dùng chưa đăng nhập, xử lý tùy ý
        }

        ratingStarts.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                myRating = v;
                Toast.makeText(vietcomment.this, "lựa chọn số sao " + myRating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDataToFirestore(ReviewItem reviewItem) {
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("rating", reviewItem.getRating());
        reviewData.put("message", reviewItem.getReview());
        reviewData.put("fullname", reviewItem.getFullName());

        firestore.collection("Reviews")
                .add(reviewData)
                .addOnSuccessListener(documentReference -> {
                    // Xử lý khi lưu thành công nếu cần
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi gặp lỗi nếu cần
                });
    }
}