package com.example.memo.view.activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.memo.R;
import com.example.memo.view.fragment.ChatFragment;
import com.example.memo.view.fragment.MypageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBottomNavigationView=findViewById(R.id.bottomNavigationView);

        //첫 화면 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.linearLayout,new ChatFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.page_chat :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linearLayout,new ChatFragment()).commit();
                break;
            case R.id.page_mypage :
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.linearLayout,new MypageFragment()).commit();
                break;
        }
        return true;
    }
}