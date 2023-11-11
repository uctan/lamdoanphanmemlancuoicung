package com.example.lamdoanphanmemlancuoicung.hoadonbanhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.Dataclass;
import com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon.hoadonclass;

import java.util.List;

public class khoahocAdapter extends RecyclerView.Adapter<khoahocAdapter.ViewHolder>  {
    private Context context;
    private List<hoadonclass> hoadonclasses;
    public khoahocAdapter (Context context, List<hoadonclass> hoadonclasses)
    {
        this.context = context;
        this.hoadonclasses = hoadonclasses;

    }
    @NonNull
    @Override
    public khoahocAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoadonitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull khoahocAdapter.ViewHolder holder, int position) {
        hoadonclass hoadonclass = hoadonclasses.get(position);
        holder.userhoadon.setText(hoadonclass.getFullname());
        holder.khoahochoadon.setText(hoadonclass.getKhoahoc());
        holder.tienhoadon.setText(String.valueOf(hoadonclass.getThanhtoan()));
    }

    @Override
    public int getItemCount() {
        return hoadonclasses.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView userhoadon, khoahochoadon, tienhoadon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userhoadon = itemView.findViewById(R.id.userhoadon);
            khoahochoadon = itemView.findViewById(R.id.khoahochoadon);
            tienhoadon = itemView.findViewById(R.id.tienhoadon);

        }
    }
}

