package com.nxg.rememberwords.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nxg.rememberwords.R;
import com.nxg.rememberwords.UpdateWordActivity;

import java.util.HashMap;
import java.util.List;

/**
 * 自定义适配器
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    //活动
    private Activity activity;
    //上下文
    private Context context;
    //放列表信息的集合
    private List<HashMap<String, Object>> list;

    //动画效果
    Animation translate_anim;

    public CustomAdapter(Activity activity, Context context, List<HashMap<String, Object>> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // 添加一个id,不是表中的id，相当于序号
        holder.word_id_item.setText(String.valueOf(list.get(position).get("id")));
        holder.worldList.setText(String.valueOf(list.get(position).get("word_list")));
        holder.translate.setText(String.valueOf(list.get(position).get("translate")));

        holder.translate.setBackgroundResource(R.drawable.item_bg_color);
        holder.translate.setPadding(-1000,0,0,0);
        final int flag[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        holder.translate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //选中
                if (flag[0] == 0) {
                    //取消
                    //v.setBackgroundResource(R.drawable.ic_emoji_emotions);
                    v.setPadding(0, 0, 0, 0);
                    v.setBackgroundColor(Color.parseColor("#00000000"));
                    flag[0] = 1;
                } else {
                    v.setPadding(-1000, 0, 0, 0);
                    v.setBackgroundResource(R.drawable.item_bg_color);
                    // v.setBackgroundColor(Color.GRAY);
                    flag[0] = 0;
                }

            }
        });

        //Android的长按钮监听事件有两个（看似），实际上只有有Listenter才是监听事件。
        //且默认会触发它的单击事件，因此应该返回true
        holder.cardView_change_button_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, UpdateWordActivity.class);
                intent.putExtra("w_id", String.valueOf(list.get(position).get("w_id")));
                intent.putExtra("word_list", String.valueOf(list.get(position).get("word_list")));
                intent.putExtra("translate", String.valueOf(list.get(position).get("translate")));
                //活动对象，请求代码
                activity.startActivityForResult(intent, 1);
                return true;
            }
        });

    }

    /**
     * 列表数
     * @return
     */
    @Override
    public int getItemCount() {
        if (list == null || list.size() <= 0) {
            int len = 0;
            return len;
        }
        //return list==null || list.size()<=0?0:list.size()
        return list.size();
    }

    /**
     * 自定义视图
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView word_id_item, worldList, translate;
        //Button changeButton;
        CardView cardView_change_button_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //绑定视图
            word_id_item = itemView.findViewById(R.id.word_id_item);
            worldList = itemView.findViewById(R.id.word_list_item);
            translate = itemView.findViewById(R.id.translate_item);

            //点击修改
            cardView_change_button_item = itemView.findViewById(R.id.cardView_change_button_item);

            //动画处理效果
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            cardView_change_button_item.setAnimation(translate_anim);
        }
    }
}
