package com.kye.dent;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailActivity extends AppCompatActivity {

    private TextView nameText, birthText, phoneText, typeText, dateTimeText;
    private Button modifyButton, cancelButton;
    private AppointmentDBHelper dbHelper;
    private int appointId;  // 예약 ID를 저장할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        // XML에서 각 TextView와 Button 연결
        nameText = findViewById(R.id.name);
        birthText = findViewById(R.id.birth);
        phoneText = findViewById(R.id.phone);
        typeText = findViewById(R.id.type);
        dateTimeText = findViewById(R.id.date);
        modifyButton = findViewById(R.id.modify_appointment_button);
        cancelButton = findViewById(R.id.cancel_appointment_button);

        dbHelper = new AppointmentDBHelper(this);

        // Intent로 전달된 예약 ID 가져오기
        appointId = getIntent().getIntExtra("appointId", -1);

        if (appointId != -1) {
            // DB에서 예약 정보 가져오기
            AppointmentDTO appointment = dbHelper.getAppointment(appointId);

            if (appointment != null) {
                // 가져온 정보를 UI에 표시
                nameText.setText(appointment.getName());
                birthText.setText(appointment.getBirthDate());
                phoneText.setText(appointment.getPhone());
                typeText.setText(appointment.getTreatmentType());
                dateTimeText.setText(appointment.getAppointmentDate());
            } else {
                Toast.makeText(this, "예약 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "잘못된 예약 ID입니다.", Toast.LENGTH_SHORT).show();
        }

        // 취소 버튼 클릭 시 예약 취소 메서드 호출
        cancelButton.setOnClickListener(view -> cancelAppointment());

        // 수정 버튼 클릭 시 예약
        modifyButton.setOnClickListener(view -> openModifyActivity());
    }


    // 예약 취소 메서드
    private void cancelAppointment() {
        if (appointId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // 예약 ID를 기준으로 삭제
            int deletedRows = db.delete(dbHelper.TABLE_APPOINTMENT, dbHelper.COLUMN_APPOINT_ID + " = ?", new String[]{String.valueOf(appointId)});

            db.close();

            if (deletedRows > 0) {
                Toast.makeText(this, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                // 메인 메뉴나 예약 목록 화면으로 이동
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "예약 취소에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "잘못된 예약 ID입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 예약 변경 화면으로 이동하는 메서드
    private void openModifyActivity() {
        Intent intent = new Intent(this, AppointmentModifyActivity.class);
        intent.putExtra("appointId", appointId);
        startActivity(intent);
    }

}
