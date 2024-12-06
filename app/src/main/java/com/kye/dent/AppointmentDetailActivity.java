package com.kye.dent;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentDetailActivity extends AppointmentActivity {

    private TextView nameText, birthText, phoneText, typeText, dateTimeText;
    private Button closeButton;
    private AppointmentDBHelper dbHelper;

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
        closeButton = findViewById(R.id.cancel_appointment_button);

        dbHelper = new AppointmentDBHelper(this);

        // Intent로 전달된 예약 ID 가져오기
        int appointId = getIntent().getIntExtra("appointId", -1);

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

        // 취소 버튼 클릭 시
        closeButton.setOnClickListener(view -> finish());
    }
}
