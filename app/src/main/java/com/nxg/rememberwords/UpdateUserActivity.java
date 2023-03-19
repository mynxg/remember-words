package com.nxg.rememberwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nxg.rememberwords.service.UserService;

import java.util.HashMap;
import java.util.List;


/**
 * 修改个人信息
 * 相当于spring+springMVC+mybatis中的controller层
 */
public class UpdateUserActivity extends AppCompatActivity {
    //用户名
    private TextView username_text;
    //昵称、邮箱 输入框
    private EditText name_input, email_input;
    //单选框
    private RadioButton male_radio, female_radio;
    private Spinner collegeSpinner;
    //保存个人信息按钮
    private Button update_user_button;
    //用户业务逻辑层
    private UserService userService;
    //用户id
    private String u_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        username_text = findViewById(R.id.username_text_view);
        name_input = findViewById(R.id.name_input);
        email_input = findViewById(R.id.email_input);
        collegeSpinner = findViewById(R.id.college_spinner);
        //男
        male_radio = findViewById(R.id.male);
        female_radio = findViewById(R.id.female);
        //更新用户信息按钮
        update_user_button = findViewById(R.id.update_user_button);

        //数据读取
        SharedPreferences data = getSharedPreferences("data", MODE_PRIVATE);
        String username = data.getString("username", "");

        userService = new UserService(UpdateUserActivity.this);
        List<HashMap<String, Object>> list = userService.findByUsername(username);

        if (list != null && list.size() > 0) {
            u_id = list.get(0).get("u_id").toString();
            //这里为什么使用try-catch呢？原因是在注册用户信息时，只是注册了用户名和密码，其他的信息都是空的，
            // 而此时name在list集合中可能是空值，不能直接tostring，有异常就得抛出异常
            String name = "";
            try {
                //从list集合中取到的值
                name = list.get(0).get("name").toString();
            } catch (Exception e) {
                name = "";
                e.printStackTrace();
            }

            username_text.setText(username);
            name_input.setText(name);

            String gender = "男";
            try {
                gender = list.get(0).get("gender").toString();
            } catch (Exception e) {
                gender = "男";
                e.printStackTrace();
            }
            male_radio.setChecked(true);
            if (gender.equals("女")) {
                female_radio.setChecked(true);
            }
            String college = " ";
            try {
                college = list.get(0).get("college").toString();
            } catch (Exception e) {
                college = "";
                e.printStackTrace();
            }
            String email = "";
            try {
                email = list.get(0).get("email").toString();
            } catch (Exception e) {
                email = "";
                e.printStackTrace();
            }
            email_input.setText(email);

            // college赋值问题，spinner赋值问题，已解决!
            int count = collegeSpinner.getCount();
            //遍历 spinner
            forSpinnerAllValue(count, college);

        }
        //保存个人信息
        update_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    /**
     * 遍历 spinner
     *
     * @param count   spinner中共有多少个值
     * @param college 大学名称
     */
    private void forSpinnerAllValue(int count, String college) {
        String str;
        for (int i = 0; i < count; i++) {
            //用循环遍历下标，获取每个值
            str = String.valueOf(collegeSpinner.getItemAtPosition(i));
            //如果要设置指定值
            if (str.equals(college)) {
                //选中第几个，就会设置第几个值
                collegeSpinner.setSelection(i, true);
            }
        }
    }

    /**
     * 提交表单
     */
    void updateUserInfo() {
        String name = name_input.getText().toString().trim();
        String email = email_input.getText().toString().trim();
        String collegeName = collegeSpinner.getSelectedItem().toString();

        String gender = "男";
        if (female_radio.isChecked()) {
            gender = "女";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("u_id", u_id);
        map.put("name", name);
        map.put("email", email);
        map.put("collegeName", collegeName);
        map.put("gender", gender);
        //修改返回 结果  -1表示失败
        long result = userService.updateUserInfo(map);
        if (result == -1) {
            Toast.makeText(UpdateUserActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(UpdateUserActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateUserActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        }
    }

}