package com.example.lamdoanphanmemlancuoicung.quanlyhoadonkhoahoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.thongtinhoadon.hoadonclass;

import java.util.List;

public class lichsukhoahocAdapter extends RecyclerView.Adapter<lichsukhoahocAdapter.ViewHolder> {

    private Context context;
    private List<hoadonclass> hoadonclassess;

    public lichsukhoahocAdapter(Context context, List<hoadonclass> hoadonclassess) {
        this.context = context;
        this.hoadonclassess = hoadonclassess;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lichsukhoahocitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hoadonclass hoadonclass = hoadonclassess.get(position);
        holder.usermua.setText(hoadonclass.getFullname());
        holder.tenkhoahoc.setText(hoadonclass.getKhoahoc());
        holder.tienmua.setText(String.valueOf(hoadonclass.getThanhtoan()));
    }

    @Override
    public int getItemCount() {
        return hoadonclassess.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usermua, tenkhoahoc, tienmua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usermua = itemView.findViewById(R.id.usermua);
            tenkhoahoc = itemView.findViewById(R.id.tenkhoahoc);
            tienmua = itemView.findViewById(R.id.tienmua);
        }
    }
}
