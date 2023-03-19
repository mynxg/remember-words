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
 * 用户service 业务层
 */
public class UserService {

    private DBOpenHelper dbOpenHelper;

    public UserService(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    //表名
    private static final String TABLE_NAME = "user";
    //id
    private static final String COLUMN_ID = "u_id";
    //用户名
    private static final String COLUMN_USERNAME = "username";
    //密码
    private static final String COLUMN_PASSWORD = "password";
    //用户 创建时间
    private static final String COLUMN_CREATE_TIME = "create_time";
    //昵称
    private static final String COLUMN_NAME = "name";
    //性别
    private static final String COLUMN_GENDER = "gender";
    //学校
    private static final String COLUMN_COLLEGE = "college";
    //邮箱
    private static final String COLUMN_EMAIL = "email";

    /**
     * 注册（添加数据）
     *
     * @param username 用户名
     * @param password 密码
     */
    public long addUser(String username, String password, String createTime) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_CREATE_TIME, createTime);
        long result = 0;
        // 判断SQLite数据库是否打开成功，才执行操作
        if (db.isOpen()) {
            result = db.insert(TABLE_NAME, null, cv);
            db.close();
        }
        return result;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public List<HashMap<String, Object>> login(String username, String password) {
        String sql = "select * from " + TABLE_NAME + " where username=? and password=?";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (db.isOpen()) {
            if (db != null) {
                Cursor cursor = db.rawQuery(sql, new String[]{username, password});
                while (cursor.moveToNext()) {
                    HashMap<String, Object> map = new HashMap<>();
                    int u_id = cursor.getColumnIndex("u_id");
                    int uname = cursor.getColumnIndex("username");
                    int name = cursor.getColumnIndex("name");
                    map.put("u_id", cursor.getString(u_id));
                    map.put("uname", cursor.getString(uname));
                    map.put("name", cursor.getString(name));
                    list.add(map);
                    db.close();
                }
            }

        }
        return list;
    }

    /**
     * 通过用户名查找用户信息
     *
     * @param username
     * @return
     */
    public List<HashMap<String, Object>> findByUsername(String username) {
        String sql = "select * from " + TABLE_NAME + " where username=?";
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<HashMap<String, Object>> list = new ArrayList<>();

        // 判断SQLite数据库是否打开成功，才执行操作
        if (db.isOpen()) {
            if (db != null) {
                Cursor cursor = db.rawQuery(sql, new String[]{username});

                while (cursor.moveToNext()) {
                    HashMap<String, Object> map = new HashMap<>();
                    int u_id = cursor.getColumnIndex("u_id");
                    int uname = cursor.getColumnIndex("username");
                    int password = cursor.getColumnIndex("password");
                    int name = cursor.getColumnIndex("name");
                    int gender = cursor.getColumnIndex("gender");
                    int college = cursor.getColumnIndex("college");
                    int email = cursor.getColumnIndex("email");

                    map.put("u_id", cursor.getString(u_id));
                    map.put("uname", cursor.getString(uname));
                    map.put("password", cursor.getString(password));
                    map.put("name", cursor.getString(name));
                    map.put("gender", cursor.getString(gender));
                    map.put("college", cursor.getString(college));
                    map.put("email", cursor.getString(email));
                    list.add(map);
                    db.close();
                }

            }
        }

        return list;
    }

    /**
     * 更新用户信息，通过用户id
     *
     * @param map
     * @return
     */
    public long updateUserInfo(HashMap<String, Object> map) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String u_id = map.get("u_id").toString();
        cv.put(COLUMN_NAME, map.get("name").toString());
        cv.put(COLUMN_EMAIL, map.get("email").toString());
        cv.put(COLUMN_COLLEGE, map.get("collegeName").toString());
        cv.put(COLUMN_GENDER, map.get("gender").toString());
        //通过用户id条件，进行修改数据
        int result = db.update(TABLE_NAME, cv, "u_id=?", new String[]{u_id});
        return result;
    }

}
