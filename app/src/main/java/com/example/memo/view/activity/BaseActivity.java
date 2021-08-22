package com.example.memo.view.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    public void gotoClass(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
