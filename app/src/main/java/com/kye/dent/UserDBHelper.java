package com.kye.dent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 2;  // 버전 증가

    public static final String TABLE_USER = "user";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_BIRTH = "birth";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                COLUMN_BIRTH + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);  // 기존 테이블 삭제
        onCreate(db);  // 테이블 재생성
    }

    public void insertUser(UserDTO user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_BIRTH, user.getBirthDate());
        db.insert(TABLE_USER, null, values);  // 데이터 삽입
        db.close();
    }

    public boolean checkUser(String name, String birth, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " +
                COLUMN_NAME + " = ? AND " +
                COLUMN_BIRTH + " = ? AND " +
                COLUMN_PHONE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name, birth, phone});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
}
