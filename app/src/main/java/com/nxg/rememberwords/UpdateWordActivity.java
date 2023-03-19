package com.nxg.rememberwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nxg.rememberwords.service.WordService;

import java.util.HashMap;
import java.util.List;

/**
 * 修改单词
 */
public class UpdateWordActivity extends AppCompatActivity {
    //TAG标签定义
    private static final String TAG = "Test---";
    //单词输入框，中文输入框，备注输入框
    private EditText word_list_input, translate_input, note_input;
    //修改单词按钮
    private Button update_word_button;
    //初始化数据库
    private WordService wordService;
    //单词id
    private String w_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word);

        word_list_input = findViewById(R.id.word_input_update);
        translate_input = findViewById(R.id.translate_input_update);
        note_input = findViewById(R.id.multiLine_note_update);
        update_word_button = findViewById(R.id.upate_word_button);
        //获得上一个页面存的值
        Intent intent = getIntent();
        w_id = intent.getStringExtra("w_id");
        //Log.d(TAG, "onCreate: " + w_id);

        wordService = new WordService(UpdateWordActivity.this);
        List<HashMap<String, Object>> listOne = wordService.findByOneWord(w_id);
        if (listOne != null && listOne.size() > 0) {
            word_list_input.setText(listOne.get(0).get("word_list").toString());
            translate_input.setText(listOne.get(0).get("translate").toString());
            String note ="";
            try {
                 note = listOne.get(0).get("note").toString();
            }catch (Exception e){
                note = "";
            }
            note_input.setText(note);
        }

        //对修改按钮设置点击事件
        update_word_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = wordService.updateWord(w_id, word_list_input.getText().toString(), translate_input.getText().toString(),
                        note_input.getText().toString());
                if (result == -1) {
                    Toast.makeText(UpdateWordActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateWordActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu_update_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 点击删除图标，删除该单词
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //这是菜单栏的删除按钮，判断点击时是否点到这个删除按钮（图片）
        if (item.getItemId() == R.id.menu_update_delete_button) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    // 删除单词需要再次确定，弹出提示框
    //显示提示框
    void confirmDialog(){
        //确认
        DialogInterface.OnClickListener confirm = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long result = wordService.deleteOneWord(w_id);
                if (result == -1) {
                    Toast.makeText(UpdateWordActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateWordActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                }
                //为什么不直接使用recreate();而使用 intent呢？
                // recreate();
                Intent intent = new Intent(UpdateWordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //取消
        DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.ListRow));
        builder.setTitle("确定删除吗?");
        builder.setCancelable(false);
        //自定义textview的颜色
        builder.setPositiveButton("确定", confirm);
        builder.setNegativeButton("取消", cancel);
        builder.show();
    }
}