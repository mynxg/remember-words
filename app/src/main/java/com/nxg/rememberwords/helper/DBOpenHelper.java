package com.nxg.rememberwords.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * SQLite工具类
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    //上下文
    private Context context;
    //数据库名
    private static final String DATABASE_NAME = "MyWord.db";
    //数据库版本
    private static final int DATABASE_VERSION = 1;

    /**
     * 用户表
     */
    //表名
    private static final String TABLE_NAME = "user";
    //id
    private static final String COLUMN_ID = "u_id";
    //用户名
    private static final String COLUMN_USERNAME = "username";
    //密码
    private static final String COLUMN_PASSWORD = "password";
    //创建时间
    private static final String COLUMN_CREATE_TIME = "create_time";
    //昵称
    private static final String COLUMN_NAME = "name";
    //性别
    private static final String COLUMN_GENDER = "gender";
    //学校
    private static final String COLUMN_COLLEGE = "college";
    //邮箱
    private static final String COLUMN_EMAIL = "email";

    //表名
    private static final String TABLE_NAME2 = "word";
    //单词id
    private static final String COLUMN_ID2 = "w_id";
    //单词
    private static final String COLUMN_WORD_LIST = "word_list";
    //翻译
    private static final String COLUMN_TRANSLATE = "w_translate";
    //备注
    private static final String COLUMN_NOTE = "w_note";

    public DBOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        //字段 u_id  username  password  create_time  name gender college  email
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," +
                COLUMN_NAME + " TEXT," + COLUMN_GENDER + " TEXT," + COLUMN_COLLEGE + " TEXT," +
                COLUMN_EMAIL + " TEXT," + COLUMN_CREATE_TIME + " DATE);";
        db.execSQL(sql);
        //创建单词表
        String sql2 = "create table " + TABLE_NAME2 + " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_WORD_LIST + " TEXT," + COLUMN_TRANSLATE + " TEXT," + COLUMN_NOTE + " TEXT);";
        db.execSQL(sql2);
        //创建表的同时向user中添加用户数据
        String insertUserSql = "insert into " + TABLE_NAME + "(username,password) values('root','123456')";
        db.execSQL(insertUserSql);
        //创建单词表的同时向word单词表中添加数据，
        String insertWordSql = "insert into " + TABLE_NAME2 + "(word_list,w_translate) values('program','n.计划；程序；节目')," +
                "('fundamental', 'adj.基础的')," +
                "('abandon', 'v.放弃,中止')," +
                "('an', '一个')," +
                "('criteria', 'n.标准')," +
                "('decision', 'n.决定')," +
                "('exceptional', 'adj.异常的')," +
                "('freedom', 'n.自由')," +
                "('wonderful', 'adj.精彩的')," +
                "('selection', 'n.选择')," +
                "('genuine', 'adj.真诚的，真挚的')," +
                "('identify', 'vt.认出;识别')," +
                "('frequently', 'adv.时常，常常')," +
                "('weather', 'n.天气')," +
                "('picture', 'vt.构想')," +
                "('necessary', 'adj.必要的；必须的')," +
                "('probably', 'adv.或许；大概')," +
                "('recommend', 'vt.推荐')," +
                "('invest', 'at.投资')," +
                "('book', 'n.书；v.预订');";

        db.execSQL(insertWordSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo 注释
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //todo 注释
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
    }
}
