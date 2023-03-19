package com.nxg.rememberwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nxg.rememberwords.service.UserService;
import com.nxg.rememberwords.util.WatchPasswordEditTextChangeTools;
import com.nxg.rememberwords.util.WatchUsernameEditTextClearTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 注册
 */
public class RegisterActivity extends AppCompatActivity {
    //用户名、密码
    private EditText username_input_rg, password_input_rg, password_input_rg2;
    //立即注册按钮
    private Button register_button_rg;
    //清除文本图标,闭眼图标，眼睛图标
    private ImageView del_register_username, del_register_password, del_register_password2;
    //用户service层
    private UserService myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_register);
        username_input_rg = findViewById(R.id.username_input_rg);
        password_input_rg = findViewById(R.id.password_input_rg);
        password_input_rg2 = findViewById(R.id.password_input_rg2);
        register_button_rg = findViewById(R.id.right_now_register);
        //清除文本图标
        del_register_username = findViewById(R.id.del_register_username);
        //闭眼图标
        del_register_password = findViewById(R.id.del_register_password);
        del_register_password2 = findViewById(R.id.del_register_password2);
        //定义的工具类
        WatchUsernameEditTextClearTools.WatchUsernameIsClear(username_input_rg, del_register_username);
        WatchPasswordEditTextChangeTools.WatchPasswordIsShow(password_input_rg, del_register_password);
        WatchPasswordEditTextChangeTools.WatchPasswordIsShow(password_input_rg2, del_register_password2);
        //立即注册
        register_button_rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_data();
            }
        });
    }

    /**
     * 获取表单信息，并提交注册信息
     */
    public void register_data() {
        String username = username_input_rg.getText().toString().trim();
        String password = password_input_rg.getText().toString().trim();
        String pass2 = password_input_rg2.getText().toString().trim();
        //判断用户名是否为空
        if (username.length() == 0) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            register_button_rg.setEnabled(true);
            return;
        }
        //判断密码是否为空
        if (password.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            register_button_rg.setEnabled(true);
            return;
        }
        myDataBaseHelper = new UserService(RegisterActivity.this);
        List<HashMap<String, Object>> byUsername = myDataBaseHelper.findByUsername(username);
        if (byUsername != null && byUsername.size() > 0) {
            Toast.makeText(this, "用户名已经存在，请更换其他用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断两次输入的密码是否一致
        if (password.equals(pass2)) {
            //如果两次密码保持一致，则将用户名和密码添加到SQLite数据库中
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String createTime = simpleDateFormat.format(date);
            //Log.i(TAG, "addUser: createTime" + createTime);

            long result = myDataBaseHelper.addUser(username, password, createTime);
            if (result == -1) {
                Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(RegisterActivity.this, "两次密码不同，请重新输入！", Toast.LENGTH_SHORT).show();
        return;
    }

}