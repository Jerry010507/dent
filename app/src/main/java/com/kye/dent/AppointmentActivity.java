package com.kye.dent;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {

    private Button appointButton, cancelButton;
    private TextView nameText, birthText, phoneText;
    private Spinner categorySpinner, yearSpinner, monthSpinner, daySpinner, hourSpinner, minuteSpinner;
    private AppointmentDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // UI 요소 초기화
        appointButton = findViewById(R.id.appoint_button);
        cancelButton = findViewById(R.id.cancel_button);
        nameText = findViewById(R.id.name);
        birthText = findViewById(R.id.birth);
        phoneText = findViewById(R.id.phone);
        categorySpinner = findViewById(R.id.category);
        yearSpinner = findViewById(R.id.years);
        monthSpinner = findViewById(R.id.months);
        daySpinner = findViewById(R.id.days);
        hourSpinner = findViewById(R.id.hours);
        minuteSpinner = findViewById(R.id.minutes);
        dbHelper = new AppointmentDBHelper(this);

        // 로그인 여부 확인
        if (isLoggedIn) {
            String name = sharedPreferences.getString("name", "");
            String birth = sharedPreferences.getString("birth", "");
            String phone = sharedPreferences.getString("phone", "");

            // 사용자 정보 TextView에 설정
            nameText.setText(name);
            birthText.setText(birth);
            phoneText.setText(phone);
        } else {
            // 로그인 정보가 없을 때 처리
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ReturnVisitActivity.class);
            startActivity(intent);
            finish();
        }

        // 스피너 설정
        setupSpinners();

        // 예약 버튼 클릭 리스너
        appointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAppointment();  // 예약 정보 저장
            }
        });

        // 취소 버튼 클릭 리스너
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();  // 현재 Activity 종료
            }
        });
    }

    // 스피너 설정 메서드
    private void setupSpinners() {
        // 카테고리 스피너 설정
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // 년도 스피너 설정
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.years, R.layout.spinner_item);
        yearAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // 월 스피너 설정
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.months, R.layout.spinner_item);
        monthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // 일 스피너 설정
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this, R.array.days, R.layout.spinner_item);
        dayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // 시 스피너 설정
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.spinner_item);
        hourAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);

        // 분 스피너 설정
        ArrayAdapter<CharSequence> minuteAdapter = ArrayAdapter.createFromResource(this, R.array.minutes, R.layout.spinner_item);
        minuteAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        minuteSpinner.setAdapter(minuteAdapter);
    }

    // 예약 정보 저장 메서드
    private void saveAppointment() {
        String name = nameText.getText().toString();
        String birth = birthText.getText().toString();
        String phone = phoneText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        // 날짜 및 시간 문자열 조합
        String date = yearSpinner.getSelectedItem().toString() + "-"
                + monthSpinner.getSelectedItem().toString() + "-"
                + daySpinner.getSelectedItem().toString();

        String time = hourSpinner.getSelectedItem().toString() + ":"
                + minuteSpinner.getSelectedItem().toString();

        // SQLite에 데이터 저장
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_NAME, name);
        values.put(dbHelper.COLUMN_BIRTH_DATE, birth);
        values.put(dbHelper.COLUMN_PHONE, phone);
        values.put(dbHelper.COLUMN_TREATMENT_TYPE, category);
        values.put(dbHelper.COLUMN_APPOINTMENT_DATE, date + " " + time);

        long rowId = db.insert(dbHelper.TABLE_APPOINTMENT, null, values);

        if (rowId != -1) {
            db.close();  // 삽입 후에 닫기

            AppointmentDTO appointment = new AppointmentDTO(name, birth, phone, date + " " + time, category);
            long appointId = dbHelper.addAppointment(appointment);

            if (appointId != -1) {
                Log.d("RESERVATION", "예약 성공, appointId: " + appointId);
                Toast.makeText(this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                // 상세 예약 화면으로 이동
                Intent intent = new Intent(this, AppointmentDetailActivity.class);
                intent.putExtra("appointId", (int) appointId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "예약 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            db.close();
            Toast.makeText(this, "데이터베이스에 예약을 삽입하는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
