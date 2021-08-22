package com.example.memo.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends BaseActivity {

    private static final String TAG = "JoinActivity";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName, mContry;
    Button mregisterBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mEmailText = findViewById(R.id.et_email);
        mPasswordText = findViewById(R.id.et_pw);
        mContry = findViewById(R.id.et_country);
        mName = findViewById(R.id.et_nickname);
        mregisterBtn = findViewById(R.id.bt_join);

        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String 변수에 내용을 넣어줌
                String strName = mName.getText().toString();
                String strPw = mPasswordText.getText().toString();
                String strEmail = mEmailText.getText().toString();
                String strCountry = mContry.getText().toString();
                //사용자 데이터베이스에 등록
                mAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //성공시
                        if (task.isSuccessful()) {
                            //사용자 정보 저장
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserData userData = new UserData();
                            userData.setIdToken(firebaseUser.getUid());
                            userData.setName(strName);
                            userData.setEmail(firebaseUser.getEmail());
                            userData.setCountry(strCountry);

                            mDatabaseRef.child("UserData").child(firebaseUser.getUid()).setValue(userData);
                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            gotoClass(MainActivity.class);
                        }
                        //실패시
                        else {
                            Toast.makeText(JoinActivity.this, "비밀번호를 6자 이상 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}