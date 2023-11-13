package com.example.lamdoanphanmemlancuoicung.quanlyhoadondatsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.admindatsan.Booking;


import java.util.List;

public class lichsuAdapter  extends RecyclerView.Adapter<lichsuAdapter.ViewHolder> {
    private Context context;
    private List<Booking> bookingg;



    public lichsuAdapter (Context context, List<Booking> bookingg)
    {
        this.context = context;
        this.bookingg = bookingg;

    }

    public void setBookings(List<Booking> bookings) {
        this.bookingg = bookings;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public lichsuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.lichsudatsanitem, parent, false);
        return new lichsuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull lichsuAdapter.ViewHolder holder, int position) {
        Booking Booking = bookingg.get(position);
        holder.userdatsan.setText(Booking.getTennguoithue());
        holder.datsanhoadon.setText(Booking.getFieldName());
        holder.tiendatsan.setText(String.valueOf(Booking.getPrice()));
        holder.ngaydatsan.setText(Booking.getDate());
        holder.tensanhoadon.setText(Booking.getTensanthue());
    }

    @Override
    public int getItemCount() {
        return bookingg.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userdatsan,datsanhoadon, tiendatsan,ngaydatsan,tensanhoadon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userdatsan = itemView.findViewById(R.id.userdatsan);
            datsanhoadon = itemView.findViewById(R.id.datsanhoadon);
            tiendatsan = itemView.findViewById(R.id.tiendatsan);
            ngaydatsan=itemView.findViewById(R.id.ngaydatsan);
            tensanhoadon = itemView.findViewById(R.id.tensanhoadon);

        }
    }
}






