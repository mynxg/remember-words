package com.nxg.rememberwords.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 用户名文本监听
 */
public class WatchUsernameEditTextClearTools {

    public static void WatchUsernameIsClear(final EditText editText, final ImageView imageView) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 监听如果输入串长度大于0那么就显示clear按钮。
                //String s1 = s + "";
                if (s.length() > 0) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 清空输入框
                editText.setText("");

            }
        });

    }

}
