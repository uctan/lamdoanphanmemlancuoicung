package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    EditText updateDesc, updateTitle, updateLang;
    String title, desc;
    int lang;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        updateImage = findViewById(R.id.updateImage);
        updateDesc = findViewById(R.id.updateDesc);
        updateTitle = findViewById(R.id.updateTitle);
        updateLang = findViewById(R.id.updateLang);
        updateButton = findViewById(R.id.updateButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateActivity.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateDesc.setText(bundle.getString("Title"));
            updateTitle.setText(bundle.getString("Description"));
            updateLang.setText(String.valueOf(bundle.getInt("Giaca")));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        if (uri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri urlImage = task.getResult();
                                imageUrl = urlImage.toString();
                                updateData();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(UpdateActivity.this, "Lỗi khi tải lên hình ảnh", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(UpdateActivity.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData() {
        title = updateTitle.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();
        String lang = updateLang.getText().toString();
        int langInt = Integer.parseInt(lang);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("products").document(key);

        Map<String, Object> data = new HashMap<>();
        data.put("dataTitle", title);
        data.put("dataDesc", desc);
        data.put("dataLang", langInt);
        data.put("dataImage", imageUrl);

        documentReference.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Xóa hình ảnh cũ
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}