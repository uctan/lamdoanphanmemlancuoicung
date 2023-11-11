package com.example.lamdoanphanmemlancuoicung.admin.adminyeuthich;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.Dataclass;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.DetailActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private Context context;
    private List<Dataclass> favoriteList;

    public FavoriteAdapter(Context context, List<Dataclass> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Dataclass dataclass = favoriteList.get(position);
        holder.favoriteTitle.setText(dataclass.getDataTitle());
        holder.favoriteLang.setText(dataclass.getDataDesc());
        Glide.with(context).load(favoriteList.get(position).getDataImage()).into(holder.favoriteImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", dataclass.getDataImage());
                intent.putExtra("Title", dataclass.getDataTitle());
                intent.putExtra("Description", dataclass.getDataDesc());
                intent.putExtra("Giaca", dataclass.getDataLang());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}

class FavoriteViewHolder extends RecyclerView.ViewHolder {
    TextView favoriteTitle, favoriteLang;
    ImageView favoriteImage;
    CardView fvrCard;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        favoriteTitle = itemView.findViewById(R.id.fvrTitle);
        favoriteLang = itemView.findViewById(R.id.fvrLang);
        fvrCard = itemView.findViewById(R.id.fvrCard);
        favoriteImage = itemView.findViewById(R.id.favoriteImage);
    }
}