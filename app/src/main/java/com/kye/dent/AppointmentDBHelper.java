package com.kye.dent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDBHelper extends SQLiteOpenHelper {

    // 데이터베이스 정보
    private static final String DATABASE_NAME = "appointment_db";
    private static final int DATABASE_VERSION = 2;

    // 테이블 및 컬럼명 정의
    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String COLUMN_APPOINT_ID = "appointId";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BIRTH_DATE = "birthDate";
    public static final String COLUMN_APPOINTMENT_DATE = "appointmentDate";
    public static final String COLUMN_TREATMENT_TYPE = "treatmentType";

    // 생성자
    public AppointmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 쿼리
        String CREATE_TABLE = "CREATE TABLE " + TABLE_APPOINTMENT + " (" +
                COLUMN_APPOINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_BIRTH_DATE + " TEXT, " +
                COLUMN_APPOINTMENT_DATE + " TEXT, " +
                COLUMN_TREATMENT_TYPE + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제 후 재생성 (버전 업그레이드 시)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
        onCreate(db);
    }

    // 예약 추가 메서드
    public long addAppointment(AppointmentDTO appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, appointment.getName());
        values.put(COLUMN_BIRTH_DATE, appointment.getBirthDate());
        values.put(COLUMN_APPOINTMENT_DATE, appointment.getAppointmentDate());
        values.put(COLUMN_TREATMENT_TYPE, appointment.getTreatmentType());
        values.put(COLUMN_PHONE, appointment.getPhone());
        long result = db.insert(TABLE_APPOINTMENT, null, values);
        db.close();
        return result;
    }

    // 특정 예약 조회 메서드
    public AppointmentDTO getAppointment(int appointId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPOINTMENT, null,
                COLUMN_APPOINT_ID + " = ?", new String[]{String.valueOf(appointId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            AppointmentDTO appointment = new AppointmentDTO(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREATMENT_TYPE))
            );
            appointment.setAppointId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINT_ID)));
            appointment.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
            cursor.close();
            return appointment;
        }
        return null;
    }

    // 전체 예약 조회 메서드
    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentDTO> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 모든 데이터를 조회하는 쿼리
        Cursor cursor = db.query(TABLE_APPOINTMENT, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Cursor로 데이터를 읽어와 DTO에 저장
                AppointmentDTO appointment = new AppointmentDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREATMENT_TYPE))
                );
                appointment.setAppointId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINT_ID)));
                appointment.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));

                // 리스트에 추가
                appointments.add(appointment);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return appointments;
    }

    // 특정 사용자의 예약 내역 가져오기
    public List<AppointmentDTO> getAppointmentsByPhone(String phone) {
        List<AppointmentDTO> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_APPOINTMENT, null,
                COLUMN_PHONE + " = ?", new String[]{phone},
                null, null, COLUMN_APPOINTMENT_DATE + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                AppointmentDTO appointment = new AppointmentDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREATMENT_TYPE))
                );
                appointment.setAppointId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINT_ID)));
                appointment.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                appointments.add(appointment);
            }
            cursor.close();
        }
        return appointments;
    }
}
