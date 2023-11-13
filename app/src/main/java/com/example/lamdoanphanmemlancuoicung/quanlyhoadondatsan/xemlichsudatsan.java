package com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.Admin;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.Booking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class xemlichsudatsan extends AppCompatActivity {

    ImageButton closelichsudatsan;
    RecyclerView recyclerViewlíchudatsan;
    lichsuAdapter adapter;

    ImageView chongayxem;

    private String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemlichsudatsan);
        closelichsudatsan = findViewById(R.id.closelichsudatsan);
        recyclerViewlíchudatsan = findViewById(R.id.recyclerViewlíchudatsan);
        chongayxem = findViewById(R.id.chongayxem);

        chongayxem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        closelichsudatsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(xemlichsudatsan.this, Admin.class);
                startActivity(intent);
            }
        });
        recyclerViewlíchudatsan.setLayoutManager(new LinearLayoutManager(this));
        List<Booking> BookingList = new ArrayList<>();
        adapter = new lichsuAdapter(this, BookingList);
        recyclerViewlíchudatsan.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference bookinggRef = db.collection("bookings");
        bookinggRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String tennguoithue = document.getString("tennguoithue");
                        String fieldName = document.getString("fieldName");
                        int price = document.getLong("price").intValue();
                        String ngaythuesan = document.getString("date");
                        String tensanthue = document.getString("tensanthue");

                        Booking booking = new Booking(fieldName, ngaythuesan, null, null, price, tensanthue, tennguoithue);
                        BookingList.add(booking);
                    }
                    adapter.notifyDataSetChanged();

                    // Sau khi cập nhật dữ liệu, tính toán và hiển thị tổng giá tiền
                    calculateAndDisplayTotal(BookingList);
                } else {
                    // Xử lý khi không thành công
                }
            }
        });


    }

    private void calculateAndDisplayTotal(List<Booking> bookingList) {
        double totalAmount = 0.0;


        for (Booking booking : bookingList) {
            totalAmount += booking.getPrice();
        }


        TextView totalAmountTextView = findViewById(R.id.totalAmountTextView);
        totalAmountTextView.setText(String.valueOf(totalAmount));
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);

                // Sau khi người dùng chọn ngày, hãy lọc danh sách và hiển thị
                filterBookingsByDate(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    private void filterBookingsByDate(String selectedDate) {
        List<Booking> filteredBookingList = new ArrayList<>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference bookinggRef = db.collection("bookings");
        bookinggRef.whereEqualTo("date", selectedDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String tennguoithue = document.getString("tennguoithue");
                                String fieldName = document.getString("fieldName");
                                int price = document.getLong("price").intValue();
                                String ngaythuesan = document.getString("date");

                                Booking booking = new Booking(fieldName, ngaythuesan, null, null, price, null, tennguoithue);

                                filteredBookingList.add(booking);
                            }
                            adapter.setBookings(filteredBookingList);
                            adapter.notifyDataSetChanged();


                            calculateAndDisplayTotal(filteredBookingList);
                        } else {

                        }
                    }
                });
    }



}
