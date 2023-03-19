package com.nxg.rememberwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * 欢迎
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
        getSupportActionBar().hide();
        //设置延时时间,3秒
        handler.sendEmptyMessageDelayed(0, 3000);

        ImageView imageView = (ImageView) findViewById(R.id.imageAnima);
        //圆形图片
        Glide.with(WelcomeActivity.this)
                .load(R.drawable.new_app)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
        //动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        //设置动画匀速运动
        LinearInterpolator li = new LinearInterpolator();
        //对动画设置插值器
        animation.setInterpolator(li);
        //对图片设置动画效果
        imageView.startAnimation(animation);
    }

    //异步消息处理
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }

    };
}