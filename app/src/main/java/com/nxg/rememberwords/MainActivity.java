package com.nxg.rememberwords;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nxg.rememberwords.adapter.CustomAdapter;
import com.nxg.rememberwords.service.WordService;

import java.util.HashMap;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    //浮动按钮
    private FloatingActionButton addButton;
    private Button menu_button;

    //初始化数据库
    private WordService wordService;

    //自定义 适配器
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add_button);
        menu_button = findViewById(R.id.user_image_menu);

        wordService = new WordService(MainActivity.this);

        List<HashMap<String, Object>> list = storeDataList();
        //创建适配器
        customAdapter = new CustomAdapter(MainActivity.this, this, list);
        //对列表设置适配器
        recyclerView.setAdapter(customAdapter);
        //设置布局管理
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //添加按钮
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });
        //如果使用ImageView或者ImageButton点击事件会失效，使用Button即可解决
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                // 跳转个人中心
                startActivity(intent);
            }
        });
    }

    /**
     * 请求响应
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //成功就重新刷新页面（创建页面活动）
        if (requestCode == 1) {
            recreate();
        }
    }

    /**
     * 存储数据集合
     *
     * @return
     */
    public List<HashMap<String, Object>> storeDataList() {
        List<HashMap<String, Object>> allWord = wordService.findAllWord();
        if (allWord == null || allWord.size() <= 0) {
            //可以显示无数据
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            return null;
        }
        return allWord;
    }

}