package com.kye.dent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {
    // 데이터베이스 정보
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 3;

    // 테이블 및 컬럼명 정의
    public static final String TABLE_USER = "user";  // 테이블 이름 'users' -> 'user'
    public static final String COLUMN_PID = "pid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_BIRTH = "birth";

    // 생성자
    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 쿼리
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_PID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_PHONE + " TEXT NOT NULL, " +
                COLUMN_BIRTH + " TEXT NOT NULL);";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // 초진 환자 등록
    public int insertUser(UserDTO user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_BIRTH, user.getBirthDate());

        long newRowId = db.insert(TABLE_USER, null, values);
        db.close();
        return (int) newRowId; // 새로 생성된 PID 반환
    }

    // 재진 환자 정보 체크
    public boolean checkUser(String name, String birth, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null,
                COLUMN_NAME + " = ? AND " +
                        COLUMN_BIRTH + " = ? AND " +
                        COLUMN_PHONE + " = ?",
                new String[]{name, birth, phone},
                null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public int getUserPID(String name, String birth, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_PID},
                COLUMN_NAME + "=? AND " + COLUMN_BIRTH + "=? AND " + COLUMN_PHONE + "=?",
                new String[]{name, birth, phone}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int pid = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID));
            cursor.close();
            return pid; // 사용자 PID 반환
        } else {
            return -1; // 사용자가 존재하지 않을 경우 -1 반환
        }
    }

    // ID값으로 정보 불러오기
    public UserDTO getUserByPID(int pid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USER,  // 테이블 이름을 'users'에서 'user'로 수정
                new String[]{COLUMN_PID, COLUMN_NAME, COLUMN_BIRTH, COLUMN_PHONE},  // 컬럼들
                COLUMN_PID + " = ?",  // 조건
                new String[]{String.valueOf(pid)},  // 조건에 맞는 PID
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            UserDTO user = new UserDTO(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            );
            cursor.close();
            return user;
        }
        cursor.close();
        return null;  // PID에 맞는 사용자가 없으면 null 반환
    }

    public UserDTO getUserByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, "phone = ?", new String[]{phone}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String birth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            cursor.close();
            return new UserDTO(userId, name, birth, phoneNumber);
        } else {
            return null;
        }
    }
}
