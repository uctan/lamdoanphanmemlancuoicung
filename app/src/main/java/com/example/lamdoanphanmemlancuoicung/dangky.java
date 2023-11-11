package com.example.lamdoanphanmemlancuoicung;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lamdoanphanmemlancuoicung.admin.Admin;
import com.example.lamdoanphanmemlancuoicung.user.manhinhuser;
import com.example.lamdoanphanmemlancuoicung.user.trangbanhang.thongtindangky;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class dangky extends AppCompatActivity {

    EditText fullName,email,password,phone;
    Button registerBtn,goToLogin;
    boolean valid = true;
    private GoogleSignInClient mGoogleSignInClient;
    private  static final int RC_SIGN_IN = 1;
    private static final String TAG = "MyActivity";

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        CheckBox isTecherBox,isStudentBox;


        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);

        isTecherBox = findViewById(R.id.isTeacher);
        isStudentBox = findViewById(R.id.isStudent);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button registerWithGmailBtn = findViewById(R.id.dangkygmail);
        registerWithGmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi phương thức để thực hiện đăng ký bằng Gmail
                registerWithGmail();
            }
        });



        isStudentBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isTecherBox.setChecked(false);
                }
            }
        });
        isTecherBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isStudentBox.setChecked(false);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);

                if (!(isTecherBox.isChecked() || isStudentBox.isChecked())){
                    Toast.makeText(dangky.this,"Lựa chọn hình thức",Toast.LENGTH_SHORT).show();
                    return;

                }

                if (valid){
                    //nguoi dung dang ky tai khoan
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(dangky.this,"Tài khoản đã được tạo",Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("User").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Fullname",fullName.getText().toString());
                            userInfo.put("UserEmail", email.getText().toString());
                            userInfo.put("PhoneNumber", phone.getText().toString());
                            userInfo.put("MatKhau", password.getText().toString());
                            // nguoi dung admin
                            if(isTecherBox.isChecked()){
                                userInfo.put("isTeacher","1");
                            }
                            if(isStudentBox.isChecked()){
                                userInfo.put("isStudent","1");
                            }
                            df.set(userInfo);
                            Intent intent = new Intent(dangky.this, thongtindangky.class);
                            intent.putExtra("UserEmail", email.getText().toString());
                            intent.putExtra("PhoneNumber", phone.getText().toString());
                            startActivity(intent);
                            if (isTecherBox.isChecked()){
                                startActivity(new Intent(getApplicationContext(), Admin.class));
                                finish();
                            }
                            if (isStudentBox.isChecked()){
                                startActivity(new Intent(getApplicationContext(), manhinhuser.class));
                                finish();
                            }


                        }
                    }).addOnFailureListener((e) -> {
                        Toast.makeText(dangky.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    });
                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), dangnhap.class));
            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
    private void registerWithGmail() {
        // Gọi intent để thực hiện đăng ký bằng Gmail
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            FirebaseUser user = fAuth.getCurrentUser();
                            // Tiếp tục xử lý tại đây (nếu cần)
                            Intent intent = new Intent(dangky.this, dangkygmail.class);
                            startActivity(intent);
                        } else {
                            // Đăng nhập thất bại
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(dangky.this, "Đăng nhập thất bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Đăng ký bằng Gmail thành công, thực hiện các bước tiếp theo
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Đăng ký bằng Gmail thất bại
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }
}