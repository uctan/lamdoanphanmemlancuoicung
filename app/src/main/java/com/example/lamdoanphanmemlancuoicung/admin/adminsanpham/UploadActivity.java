package com.example.lamdoanphanmemlancuoicung.admin.adminsanpham;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.lamdoanphanmemlancuoicung.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTopic, uploadDesc, uploadLang;
    String imageURL;
    Uri uri;
    private static int currentProductID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadImage);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadTopic = findViewById(R.id.uploadTopic);
        uploadLang = findViewById(R.id.uploadLang);

        saveButton = findViewById(R.id.saveButton);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code để chọn hình ảnh
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");

                // Sử dụng ActivityResultLauncher để nhận kết quả từ Intent
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        String title = uploadTopic.getText().toString();
        String desc = uploadDesc.getText().toString();
        int lang = Integer.parseInt(uploadLang.getText().toString());

        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                    .child(Objects.requireNonNull(uri.getLastPathSegment()));

            AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();

            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnCompleteListener(task -> {
                    Uri urlImage = task.getResult();
                    imageURL = urlImage.toString();
                    currentProductID++; // Tăng giá trị currentProductID
                    uploadData(title, desc, lang, currentProductID); // Truyền currentProductID vào hàm uploadData
                    dialog.dismiss();
                });
            }).addOnFailureListener(e -> {
                dialog.dismiss();
                Toast.makeText(UploadActivity.this, "Lỗi khi tải lên hình ảnh", Toast.LENGTH_SHORT).show();
            });
        } else {
            // Handle the case where the uri is null (no image selected)
            Toast.makeText(UploadActivity.this, "Lựa chọn hình ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadData(String title, String desc, int lang, int productID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsCollection = db.collection("products");

        Dataclass dataclass = new Dataclass(productID, title, desc, lang, imageURL, false);

        String currentDate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());

        Query query = productsCollection.whereEqualTo("dataTitle",  currentDate);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Sản phẩm đã tồn tại, cập nhật thông tin
                    DocumentReference docRef = querySnapshot.getDocuments().get(0).getReference();
                    docRef.update("dataDesc", desc);
                    docRef.update("dataLang", lang);
                    docRef.update("dataImage", imageURL);
                } else {
                    // Sản phẩm chưa tồn tại, thêm mới
                    productsCollection.add(dataclass);
                }
                Toast.makeText(UploadActivity.this, "Lưu thông tin", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UploadActivity.this, "Lỗi khi lưu thông tin", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}