package com.nxg.rememberwords.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nxg.rememberwords.R;

/**
 * 密码文本监听
 */
public class WatchPasswordEditTextChangeTools {

    public static void WatchPasswordIsShow(final EditText editText, final ImageView imageView) {

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
                // 监听如果输入的字符串长度大于0，那么就显示闭眼图标。
                String s1 = s + "";
                if (s.length() > 0) {
                    imageView.setBackgroundResource(R.drawable.eye_close_icon);
                    //显示闭眼图标
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    //隐藏图标
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Drawable drawable = resources.getDrawable(R.drawable.eye_closes);
                m1.setImageDrawable(drawable);*/
                //获得
                TransformationMethod method = editText.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    //将明文设置为密文
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //eye_close_icon是表示隐藏密码的“眼睛+斜杠”图标
                    imageView.setBackgroundResource(R.drawable.eye_close_icon);
                } else {
                    // 改变显示明文
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //eye_fill_icon是表示显示密码的“眼睛”图标
                    imageView.setBackgroundResource(R.drawable.eye_fill_icon);
                }



            }
        });

    }

}
