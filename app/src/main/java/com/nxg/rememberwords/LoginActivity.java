package com.nxg.rememberwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.nxg.rememberwords.util.WatchUsernameEditTextClearTools;
import com.nxg.rememberwords.service.UserService;
import com.nxg.rememberwords.util.WatchPasswordEditTextChangeTools;

import java.util.HashMap;
import java.util.List;

/**
 * 登录
 */
public class LoginActivity extends AppCompatActivity {
    //图标
    private ImageView loginImageView;
    //用户名、密码
    private EditText usernameInput, passwordInput;

    //注册按钮
    private TextView registerButton;
    //登录按钮
    private Button loginButton;
    //清除用户名输入框中的数据 图标
    private ImageView del_r_username;
    //显示 密码图标
    private ImageView del_r_password;
    //初始化数据库
    private UserService userService;
    //用户名
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_login_r);

        //设置全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
        getSupportActionBar().hide();

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_text);
        loginButton = findViewById(R.id.login_button);
        //用户输入框中的图标监听
        del_r_username = findViewById(R.id.del_r_username);
        del_r_password = findViewById(R.id.del_r_password);
        //创建User对象
        userService = new UserService(LoginActivity.this);
        //设置圆形图片
        loginImageView = findViewById(R.id.loginImageView);
        Glide.with(LoginActivity.this)
                .load(R.drawable.new_app)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(loginImageView);

        // 对用户名、密码输入框  添加监听事件
        WatchUsernameEditTextClearTools.WatchUsernameIsClear(usernameInput, del_r_username);
        WatchPasswordEditTextChangeTools.WatchPasswordIsShow(passwordInput, del_r_password);

        //注册按钮设置点击事件
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //对登录按钮设置点击事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
    }

    void showResult() {
        //验证用户名和密码是否正确
        username = usernameInput.getText().toString();
        String pass = passwordInput.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        List<HashMap<String, Object>> byUsername = userService.findByUsername(username);
        if (byUsername == null || byUsername.size() <= 0) {
            Toast.makeText(LoginActivity.this, "用户名不存在！", Toast.LENGTH_SHORT).show();
            return;
        }

        List<HashMap<String, Object>> loginList = userService.login(username, pass);
        if (!loginList.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //持久化存储，  MODE_PRIVATE表示0，私有的，只能在当前应用使用
            SharedPreferences.Editor data = getSharedPreferences("data", MODE_PRIVATE).edit();
            data.putString("username", username);
            data.apply();

            startActivity(intent);
        }
        String password = byUsername.get(0).get("password").toString();
        if (!password.equals(pass)) {
            Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}