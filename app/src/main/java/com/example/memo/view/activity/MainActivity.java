package com.example.memo.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memo.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    Button bt_join, bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_join = findViewById(R.id.bt_join);
        bt_login = findViewById(R.id.bt_login);

        bt_join.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_join :
                gotoClass(ChatingActivity.class);
                break;
            case R.id.bt_login:
                gotoClass(HomeActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v);
        }
    }
}