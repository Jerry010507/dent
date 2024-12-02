package com.kye.dent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kye.dent.TreatmentDTO;

import java.util.ArrayList;
import java.util.List;

public class TreatmentDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dentistry.db";
    public static final int DATABASE_VERSION = 1;

    public TreatmentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTreatmentTable = "CREATE TABLE treatment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "birthdate TEXT, " +
                "date TEXT, " +
                "treatmentType TEXT, " +
                "doctor TEXT, " +
                "notes TEXT, " +
                "cost TEXT)";
        db.execSQL(createTreatmentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS treatment");
        onCreate(db);
    }

    // 진료 내역 추가
    public void addTreatment(TreatmentDTO treatment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", treatment.getPatientName());
        values.put("birthdate", treatment.getBirthDate());
        values.put("date", treatment.getTreatmentDate());
        values.put("treatmentType", treatment.getTreatmentType());
        values.put("doctor", treatment.getDoctorName());
        values.put("notes", treatment.getDoctorNotes());
        values.put("cost", treatment.getTreatmentCost());

        db.insert("treatment", null, values);
        db.close();
    }

    // 진료 내역 가져오기
    public List<TreatmentDTO> getTreatments() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("treatment", null, null, null, null, null, null);

        List<TreatmentDTO> treatments = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TreatmentDTO treatment = new TreatmentDTO(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("birthdate")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("treatmentType")),
                        cursor.getString(cursor.getColumnIndexOrThrow("doctor")),
                        cursor.getString(cursor.getColumnIndexOrThrow("notes")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))
                );
                treatments.add(treatment);
            }
            cursor.close();
        }
        db.close();
        return treatments;
    }
}

