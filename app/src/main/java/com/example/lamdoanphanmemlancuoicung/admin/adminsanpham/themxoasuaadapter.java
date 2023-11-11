package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.DetailActivity;

import java.util.List;

public class themxoasuaadapter extends RecyclerView.Adapter<themxoasuaViewHolder> {
    private Context context;
    private List<Dataclass> dataList;

    public themxoasuaadapter(Context context, List<Dataclass> dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public themxoasuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new themxoasuaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull themxoasuaViewHolder holder, int position) {
        Dataclass data = dataList.get(position);

        Glide.with(context).load(data.getDataImage()).into(holder.recImage);
        holder.recTitle.setText(data.getDataTitle());
        holder.recLang.setText(String.valueOf(data.getDataLang()));

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, themxoasuadetail.class);
                intent.putExtra("Image", data.getDataImage());
                intent.putExtra("Title", data.getDataTitle());
                intent.putExtra("Description", data.getDataDesc());
                intent.putExtra("Giaca", data.getDataLang());
                intent.putExtra("Key", data.getKey());

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
class themxoasuaViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recLang;
    CardView recCard;
    ImageButton favoriteIcon;

    public themxoasuaViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recLang = itemView.findViewById(R.id.recLang);
        recCard = itemView.findViewById(R.id.recCard);
        favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
    }
}

