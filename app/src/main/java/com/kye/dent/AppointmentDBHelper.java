package com.kye.dent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dentistry.db";
    public static final int DATABASE_VERSION = 1;

    public AppointmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAppointmentTable = "CREATE TABLE appointment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "birthdate TEXT, " +
                "date TEXT, " +
                "treatmentType TEXT)";
        db.execSQL(createAppointmentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS appointment");
        onCreate(db);
    }

    // 예약 내역 추가
    public void addAppointment(AppointmentDTO appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", appointment.getPatientName());
        values.put("birthdate", appointment.getBirthDate());
        values.put("date", appointment.getAppointmentDate());
        values.put("treatmentType", appointment.getTreatmentType());

        db.insert("appointment", null, values);
        db.close();
    }

    // 예약 내역 가져오기
    public List<AppointmentDTO> getAppointments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("appointment", null, null, null, null, null, null);

        List<AppointmentDTO> appointments = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AppointmentDTO appointment = new AppointmentDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("birthdate")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("treatmentType"))
                );
                appointments.add(appointment);
            }
            cursor.close();
        }
        db.close();
        return appointments;
    }
}
