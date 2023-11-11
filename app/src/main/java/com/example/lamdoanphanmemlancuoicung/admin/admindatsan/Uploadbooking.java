    package com.example.lamdoanphanmemlancuoicung.admin.admindatsan;
    
    import androidx.activity.result.ActivityResult;
    import androidx.activity.result.ActivityResultCallback;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;
    
    import com.example.lamdoanphanmemlancuoicung.R;
    import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.Dataclass;
    import com.example.lamdoanphanmemlancuoicung.admin.adminsanpham.UploadActivity;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.CollectionReference;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.Query;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;

    import java.util.Objects;
    import java.util.UUID;

    public class Uploadbooking extends AppCompatActivity {

        ImageView uploadImagethuesan;
        EditText uploadthuesan;
        Button luuButton;
        Uri imageUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_uploadbooking);

            uploadImagethuesan = findViewById(R.id.uploadImagethuesan);
            uploadthuesan = findViewById(R.id.uploadthuesan);
            luuButton = findViewById(R.id.luuButton);

            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                imageUri = data.getData();
                                uploadImagethuesan.setImageURI(imageUri);
                            } else {
                                Toast.makeText(Uploadbooking.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

            uploadImagethuesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPicker = new Intent(Intent.ACTION_PICK);
                    photoPicker.setType("image/*");
                    activityResultLauncher.launch(photoPicker);
                }
            });

            luuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    savethuesan();
                }
            });
        }

        public void savethuesan() {
            String name = uploadthuesan.getText().toString();
            if (imageUri != null) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                        .child("Football_Images")
                        .child(UUID.randomUUID().toString()); // Đặt tên ngẫu nhiên cho hình ảnh

                AlertDialog.Builder builder = new AlertDialog.Builder(Uploadbooking.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    uriTask.addOnCompleteListener(task -> {
                        Uri urlImage = task.getResult();
                        String imageURL = urlImage.toString();
                        uploadData(name, imageURL);
                        dialog.dismiss();
                    });
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(Uploadbooking.this, "Lỗi khi tải lên hình ảnh", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(Uploadbooking.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }

        public void uploadData(String name, String imageURL) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference productsCollection = db.collection("football_fields");

            Query query = productsCollection.whereEqualTo("name", name);
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
                        docRef.update("imageResourceId", imageURL);
                    } else {
                        FootballField footballField = new FootballField(name, imageURL); // Đúng cú pháp để tạo đối tượng FootballField
                        productsCollection.add(footballField);
                    }
                    Toast.makeText(Uploadbooking.this, "Lưu thông tin", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Uploadbooking.this, "Lỗi khi lưu thông tin", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(Uploadbooking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }