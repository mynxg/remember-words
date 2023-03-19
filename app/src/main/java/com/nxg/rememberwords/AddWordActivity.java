package com.nxg.rememberwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nxg.rememberwords.service.WordService;

/**
 * 增加单词
 */
public class AddWordActivity extends AppCompatActivity {
    //单词输入框、中文输入框、备注输入框
    private EditText wordListInput, translateInput, noteInput;
    //保存按钮
    private Button add_submit_button;
    //初始化数据库
    private WordService myWordDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        wordListInput = findViewById(R.id.word_list_input);
        translateInput = findViewById(R.id.translate_input);
        noteInput = findViewById(R.id.note_input);
        add_submit_button = findViewById(R.id.add_submit_button);
        //添加单词
        add_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化SQLite工具类
                myWordDataHelper = new WordService(AddWordActivity.this);

                String wordList = wordListInput.getText().toString().trim();
                String translate = translateInput.getText().toString().trim();
                String note = noteInput.getText().toString().trim();

                long result = myWordDataHelper.addWord(wordList, translate, note);
                if (result == -1) {
                    Toast.makeText(AddWordActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(AddWordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AddWordActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}