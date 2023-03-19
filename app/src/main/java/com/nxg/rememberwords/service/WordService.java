package com.nxg.rememberwords.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nxg.rememberwords.helper.DBOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 单词业务层（service）
 */
public class WordService {

    private Context context;
    private DBOpenHelper dbOpenHelper;

    public WordService(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * 单词表
     */
    //单词 表名
    private static final String  TABLE_NAME2 = "word";
    //单词id
    private static final String COLUMN_ID2 = "w_id";
    //单词
    private static final String COLUMN_WORD_LIST = "word_list";
    //翻译
    private static final String COLUMN_TRANSLATE = "w_translate";
    //备注
    private static final String COLUMN_NOTE = "w_note";

    /**
     * 增加一个单词
     * @param word_list 单词
     * @param translate 翻译
     * @param note 备注
     */
    public long addWord(String word_list,String translate,String note){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORD_LIST,word_list);
        cv.put(COLUMN_TRANSLATE,translate);
        cv.put(COLUMN_NOTE,note);

        long result = 0;
        // 判断SQLite数据库是否打开成功，才放心操作一下操作
        if (db.isOpen()) {
            result = db.insert(TABLE_NAME2, null, cv);
            db.close();
        }
        return result;
    }

    /**
     * 查询所有单词
     * @return Cursor对象
     */
    public List<HashMap<String,Object>> findAllWord(){
        //升序 ASC ，降序DESC
        String sql = "select * from "+TABLE_NAME2+" order by word_list ASC ";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<HashMap<String,Object>> list =new ArrayList<HashMap<String,Object>>();
        // 判断SQLite数据库是否打开成功，才放心操作一下操作
        if (db.isOpen()) {

            if(db!=null){
                Cursor cursor = db.rawQuery(sql,null);
                if (cursor.getCount() == 0){
                    return null;
                }
                int i=1;
                while (cursor.moveToNext()){
                    int id = cursor.getColumnIndex("w_id");
                    int word_list = cursor.getColumnIndex("word_list");
                    int translate = cursor.getColumnIndex("w_translate");
                    int note = cursor.getColumnIndex("w_note");

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("w_id",cursor.getString(id));
                    map.put("id",i++);
                    map.put("word_list",cursor.getString(word_list));
                    map.put("translate",cursor.getString(translate));
                    map.put("note",cursor.getString(note));
                    list.add(map);
                }
                db.close();
            }
        }

        return list;
    }

    /**
     * 查询单个单词，通过单词id查询
     * @param w_id 单词id
     * @return
     */
    public List<HashMap<String,Object>> findByOneWord(String w_id){
        String sql = "select * from "+TABLE_NAME2+" where w_id=?";
        List<HashMap<String,Object>> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 判断SQLite数据库是否打开成功，才放心操作一下操作
        if (db.isOpen()) {
            if(db!=null){
                Cursor cursor = db.rawQuery(sql,new String[]{w_id});
                if (cursor.getCount() == 0){
                    return null;
                }
                while (cursor.moveToNext()){
                    int id = cursor.getColumnIndex("w_id");
                    int word_list = cursor.getColumnIndex("word_list");
                    int translate = cursor.getColumnIndex("w_translate");
                    int note = cursor.getColumnIndex("w_note");

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("w_id",cursor.getString(id));
                    map.put("word_list",cursor.getString(word_list));
                    map.put("translate",cursor.getString(translate));
                    map.put("note",cursor.getString(note));
                    list.add(map);
                }
                //关闭连接
                db.close();
            }

        }
        return list;
    }

    /**
     * 修改单词
     * @param w_id 单词id
     * @param word_list 单词
     * @param translate 翻译
     * @param note 备注
     */
    public long updateWord(String w_id,String word_list,String translate,String note){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORD_LIST,word_list);
        cv.put(COLUMN_TRANSLATE,translate);
        cv.put(COLUMN_NOTE,note);

        long result = -1;
        // 判断SQLite数据库是否打开成功，才放心操作一下操作
        if (db.isOpen()) {
            if(db!=null){
                result = db.update(TABLE_NAME2,cv,"w_id=?",new String[]{w_id});
            }
            db.close();
        }

       return result;
    }

    /**
     * 删除单个单词
     * @param w_id 单词id
     */
    public long deleteOneWord(String w_id){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long result = -1;
        // 判断SQLite数据库是否打开成功，才放心操作一下操作
        if (db.isOpen()) {
            if(db!=null){
                 result = db.delete(TABLE_NAME2,"w_id=?",new String[]{w_id});
            }
            db.close();
        }

        return result;
    }

    /**
     * 删除所有单词
     */
    public void deleteAllWord(){
        String sql = "delete from "+TABLE_NAME2;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL(sql);
    }
}
