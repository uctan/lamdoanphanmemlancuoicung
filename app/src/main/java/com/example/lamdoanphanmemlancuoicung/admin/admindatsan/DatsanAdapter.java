package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;

import java.util.List;

public class DatsanAdapter extends RecyclerView.Adapter<DatsanAdapter.ViewHolder> {

    private List<FootballField> footballFields;
    private OnItemClickListener onItemClickListener;

    public DatsanAdapter(List<FootballField> footballFields) {
        this.footballFields = footballFields;
    }

    public interface OnItemClickListener {
        void onItemClick(FootballField footballField);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thuesan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FootballField footballField = footballFields.get(position);

        // Hiển thị tên sân bóng
        holder.thuesanTitle.setText(footballField.getName());

        // Kiểm tra nếu có URL hợp lệ trước khi sử dụng Glide
        String imageUrl = footballField.getImageResourceId();
        if (isValidUrl(imageUrl)) {
            // Sử dụng Glide để tải hình ảnh từ URL và hiển thị vào ImageView
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.thuesanImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    FootballField clickedField = footballFields.get(position);

                    // Tạo Intent
                    Intent intent = new Intent(holder.itemView.getContext(), adminthuesan.class);

                    intent.putExtra("name", clickedField.getName());

                    // Chuyển qua activity adminthuesan
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return footballFields.size();
    }

    public void updateFootballFields(List<FootballField> newFootballFields) {
        footballFields.clear();
        footballFields.addAll(newFootballFields);
        notifyDataSetChanged();
    }

    private boolean isValidUrl(String url) {
        return url != null && !url.isEmpty();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thuesanImage;
        TextView thuesanTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thuesanImage = itemView.findViewById(R.id.thuesanImage);
            thuesanTitle = itemView.findViewById(R.id.thuesanTitle);
        }
    }
}