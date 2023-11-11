package com.example.lamdoanphanmemlancuoicung.user.trangbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham.ReviewItem;
import com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham.vietcomment;
import com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon.hoadonclass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class thongtindangky extends AppCompatActivity {
    private TextView idten, idtsdt, idkhoahoc, idthanhtoan;
    ImageButton savett, vectorttshow,thanhtoanmomo;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    //thanh toanmomo
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "HoangNgoc";
    private String description = "mua hàng online";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtindangky);
        idten = findViewById(R.id.idten);


        idtsdt = findViewById(R.id.idtsdt);


        idkhoahoc = findViewById(R.id.idkhoahoc);
        idkhoahoc.setText(getIntent().getStringExtra("Title"));

        idthanhtoan = findViewById(R.id.idthanhtoan);
        idthanhtoan.setText(getIntent().getStringExtra("Giaca"));
        savett = findViewById(R.id.savett);
        //momo
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        thanhtoanmomo = findViewById(R.id.thanhtoanmomo);




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
                        idten.setText(fullName);
                        idtsdt.setText(userEmail);


                    }
                }
            });

        }
        savett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("HoaDon")
                        .orderBy("idDonHang", Query.Direction.DESCENDING)
                        .limit(1)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int newIdDonHang = 1;
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        int highestidDonHang = document.getLong("idDonHang").intValue();
                                        newIdDonHang = highestidDonHang + 1;
                                    }
                                }

                                String ten = idten.getText().toString();
                                String sdt = idtsdt.getText().toString();
                                String khoahoc = idkhoahoc.getText().toString();
                                String thanhtoanStr = idthanhtoan.getText().toString();
                                int thanhtoan = Integer.parseInt(thanhtoanStr);

                                // Tạo một đối tượng hoadonclass với thông tin đã nhập
                                hoadonclass hoadon = new hoadonclass(newIdDonHang, ten, sdt, khoahoc, thanhtoan);

                                fStore.collection("Hoadon")
                                        .add(hoadon)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(thongtindangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Xử lý khi gặp lỗi nếu cần
                                        });
                            }
                        });
            }
        });
        thanhtoanmomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("HoaDon")
                        .orderBy("idDonHang", Query.Direction.DESCENDING)
                        .limit(1)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int newIdDonHang = 1;
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        int highestidDonHang = document.getLong("idDonHang").intValue();
                                        newIdDonHang = highestidDonHang + 1;
                                    }
                                }

                                String ten = idten.getText().toString();
                                String sdt = idtsdt.getText().toString();
                                String khoahoc = idkhoahoc.getText().toString();
                                String thanhtoanStr = idthanhtoan.getText().toString();
                                int thanhtoan = Integer.parseInt(thanhtoanStr);

                                // Tạo một đối tượng hoadonclass với thông tin đã nhập
                                hoadonclass hoadon = new hoadonclass(newIdDonHang, ten, sdt, khoahoc, thanhtoan);

                                fStore.collection("Hoadon")
                                        .add(hoadon)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(thongtindangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                            requestPayment(merchantName);
                                            amount = String.valueOf(thanhtoan);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Xử lý khi gặp lỗi nếu cần
                                        });
                            }
                        });
            }
        });
    }

    //momo
    private void requestPayment(String idDonHang) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);



        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, description);
        //client Optional
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);

        eventValue.put(MoMoParameterNamePayment.REQUEST_ID,  merchantCode+"-"+ UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "MOMOC2IC20220510");

        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
            objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    Log.d("Thanhcong", data.getStringExtra("message"));

                    if(data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:

                    } else {
                        Log.d("Thanhcong", data.getStringExtra("Không nhận được thông tin"));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("Thanhcong", data.getStringExtra("Không thành công"));
                } else if(data.getIntExtra("status", -1) == 2) {
                    Log.d("Thanhcong", data.getStringExtra("Không thành công"));
                } else {
                    Log.d("Thanhcong", data.getStringExtra("Không thành công"));
                }
            } else {
                Log.d("Thanhcong", data.getStringExtra("Không thành công"));
            }
        } else {
            Log.d("Thanhcong", data.getStringExtra("Không thành công"));
        }
    }


}