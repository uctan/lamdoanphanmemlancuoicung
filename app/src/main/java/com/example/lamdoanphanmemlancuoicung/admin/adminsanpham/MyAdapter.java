package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import android.util.Log;
import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;
import static com.google.firebase.firestore.DocumentChange.Type.REMOVED;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.DetailActivity;
import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Dataclass> dataList;
    private List<Boolean> isFavoriteList;

    public MyAdapter(Context context, List<Dataclass> dataList, List<Boolean> isFavoriteList) {
        this.context = context;
        this.dataList = dataList;
        this.isFavoriteList = isFavoriteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dataclass data = dataList.get(position);

        Glide.with(context).load(data.getDataImage()).into(holder.recImage);
        holder.recTitle.setText(data.getDataTitle());
        holder.recLang.setText(String.valueOf(data.getDataLang()));

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", data.getDataImage());
                intent.putExtra("Title", data.getDataTitle());
                intent.putExtra("Description", data.getDataDesc());
                intent.putExtra("Giaca", data.getDataLang());
                context.startActivity(intent);
            }
        });

        boolean isFavorite = isFavoriteList.get(position);
        holder.favoriteIcon.setImageResource(isFavorite ? R.drawable.redstart : R.drawable.start);

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean currentFavorite = isFavoriteList.get(position);
                isFavoriteList.set(position, !currentFavorite);
                updateFavoriteStatus(data.getDataTitle(), !currentFavorite);
                notifyItemChanged(position);
            }
        });
    }
    public void updateFavoriteStatus(String dataTitle, boolean favorite) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("products");

        Query query = collectionRef.whereEqualTo("dataTitle", dataTitle);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    DocumentReference docRef = document.getReference();

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("favorite", favorite);

                    docRef.update(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Cập nhật thành công
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý lỗi
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(List<Dataclass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recLang;
    CardView recCard;
    ImageButton favoriteIcon;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recLang = itemView.findViewById(R.id.recLang);
        recCard = itemView.findViewById(R.id.recCard);
        favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
    }
}