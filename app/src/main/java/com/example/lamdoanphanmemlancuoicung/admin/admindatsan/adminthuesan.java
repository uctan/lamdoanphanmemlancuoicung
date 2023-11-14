package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class adminthuesan extends AppCompatActivity {

    TextView sanbongda, giatienthuesan, ngay, datdau, ketthuc;
    ImageView sanA, sanD, sanB, sanC;
    View savethuesan;
    ImageView giobatdau, gioketthuc;

    ImageView selectedSan = null;
    FirebaseFirestore db;
    TextView tenSanTextView;
    HashMap<String, Integer> giaSan = new HashMap<>();
    View quaylaitrangchonsan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminthuesan);


        tenSanTextView =findViewById(R.id.ten_san);
        db = FirebaseFirestore.getInstance();
        sanbongda = findViewById(R.id.sanbongda);
        sanA = findViewById(R.id.sanA);
        sanB = findViewById(R.id.sanB);
        sanC = findViewById(R.id.sanC);
        sanD = findViewById(R.id.sanD);
        savethuesan = findViewById(R.id.savethuesan);
        giatienthuesan = findViewById(R.id.giatienthuesan);
        ngay = findViewById(R.id.ngay);
        datdau = findViewById(R.id.batdau);
        ketthuc = findViewById(R.id.ketthuc);
        giobatdau = findViewById(R.id.giobatdau);
        gioketthuc = findViewById(R.id.gioketthuc);

        giaSan.put("Sân A", 100000);
        giaSan.put("Sân B", 150000);
        giaSan.put("Sân C", 120000);
        giaSan.put("Sân D", 130000);

        quaylaitrangchonsan = findViewById(R.id.quaylaitrangchonsan);
        quaylaitrangchonsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminthuesan.this, giaodienthuesan.class);
                startActivity(intent);
            }
        });

        sanA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiTenSan("Sân A");
                changeBackgroundColor(sanA);
            }
        });

        sanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiTenSan("Sân B");
                changeBackgroundColor(sanB);
            }
        });

        sanC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiTenSan("Sân C");
                changeBackgroundColor(sanC);
            }
        });

        sanD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiTenSan("Sân D");
                changeBackgroundColor(sanD);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("name");
            if (name != null) {
                sanbongda.setText(name);
            }
        }

        ImageView chongay = findViewById(R.id.chongay);
        chongay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        giobatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog("batdau");
            }
        });

        gioketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog("ketthuc");
            }
        });

        savethuesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveBookingInfo();





            }
        });
    }

    private void saveBookingInfo() {
        String hienThiTenSan = tenSanTextView.getText().toString();
        String selectedDate = ngay.getText().toString();
        String gioBatDau = datdau.getText().toString();
        String gioKetThuc = ketthuc.getText().toString();
        String name = sanbongda.getText().toString();

        int gia = Integer.parseInt(giatienthuesan.getText().toString().replace(" VND", ""));

        int gioBatDauInt = Integer.parseInt(gioBatDau.replace(":", ""));
        int gioKetThucInt = Integer.parseInt(gioKetThuc.replace(":", ""));

        boolean isBookingConflict = checkBookingConflict(selectedDate, gioBatDau, gioKetThuc);

        if (isBookingConflict) {
            Toast.makeText(this, "Lịch đã bị đặt, vui lòng chọn thời gian khác.", Toast.LENGTH_SHORT).show();
        } else {
            // Intent chỉ được thực hiện nếu không có xung đột lịch đặt
            Intent intent = new Intent(adminthuesan.this, thongtinticket.class);
            intent.putExtra("tenSan", hienThiTenSan);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("gioBatDau", gioBatDau);
            intent.putExtra("gioKetThuc", gioKetThuc);
            intent.putExtra("gia", gia);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

    private boolean checkBookingConflict(String selectedDate, String gioBatDau, String gioKetThuc) {
        boolean isConflict = false;



        return isConflict;
    }


    private void hienThiTenSan(String tenSan) {
        TextView tenSanTextView = findViewById(R.id.ten_san);
        tenSanTextView.setText(tenSan);

        Integer gia = giaSan.get(tenSan);
        if (gia != null) {
            giatienthuesan.setText(gia + " VND");
        } else {
            giatienthuesan.setText("Giá sân");
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                ngay.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void showTimePickerDialog(final String type) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);

                if (type.equals("batdau")) {
                    datdau.setText(selectedTime);
                } else if (type.equals("ketthuc")) {
                    ketthuc.setText(selectedTime);
                }
            }
        }, hourOfDay, minute, true);

        timePickerDialog.show();
    }

    private void changeBackgroundColor(ImageView selectedView) {

        if (selectedSan != null && selectedSan != selectedView) {
            selectedSan.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        if (selectedView != null) {
            selectedView.setBackgroundColor(Color.parseColor("#484F88"));
            selectedSan = selectedView;
        }

    }

}