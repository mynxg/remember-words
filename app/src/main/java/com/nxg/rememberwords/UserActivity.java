package com.nxg.rememberwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户中心
 * 用户名，在数据库中是不允许重复的
 */
public class UserActivity extends AppCompatActivity {
    //个人信息图片按钮
    private ImageView userInfoImageView;
    private TextView usernameTextView;
    //退出登录按钮
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userInfoImageView = findViewById(R.id.userInfoImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        signOutButton = findViewById(R.id.signOutButton);
        //读取数据，
        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
        String username = data.getString("username", "");
        usernameTextView.setText(username);

        //对图片按钮设置点击事件。
        userInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, UpdateUserActivity.class);
                //todo 存储用户名，用户名是唯一的）
                // intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        //退出登录按钮
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}