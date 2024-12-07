package com.kye.dent;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentModifyActivity extends AppCompatActivity {

    private TextView nameEdit, birthEdit, phoneEdit;
    private Spinner treatmentTypeSpinner, yearSpinner, monthSpinner, daySpinner, hourSpinner, minuteSpinner;
    private Button updateButton;
    private AppointmentDBHelper dbHelper;
    private int appointId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_modify);

        // 스피너와 버튼 초기화
        nameEdit = findViewById(R.id.name_edit);
        birthEdit = findViewById(R.id.birth_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        treatmentTypeSpinner = findViewById(R.id.type_edit);
        yearSpinner = findViewById(R.id.years_edit);
        monthSpinner = findViewById(R.id.months_edit);
        daySpinner = findViewById(R.id.days_edit);
        hourSpinner = findViewById(R.id.hours_edit);
        minuteSpinner = findViewById(R.id.minutes_edit);
        updateButton = findViewById(R.id.update_button);

        dbHelper = new AppointmentDBHelper(this);

        // 인텐트에서 예약 ID 가져오기
        appointId = getIntent().getIntExtra("appointId", -1);

        if (appointId != -1) {
            // DB에서 예약 정보 가져오기
            AppointmentDTO appointment = dbHelper.getAppointment(appointId);

            if (appointment != null) {
                // 가져온 정보를 UI에 표시
                nameEdit.setText(appointment.getName());
                birthEdit.setText(appointment.getBirthDate());
                phoneEdit.setText(appointment.getPhone());
            } else {
                Toast.makeText(this, "예약 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "잘못된 예약 ID입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 스피너에 데이터 설정
        setupSpinners();

        // 업데이트 버튼 클릭 리스너
        updateButton.setOnClickListener(v -> updateAppointment());
    }

    // 스피너 설정 메서드
    private void setupSpinners() {
        // 카테고리 스피너 설정
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        treatmentTypeSpinner.setAdapter(categoryAdapter);

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

    private void updateAppointment() {
        String newTreatmentType = treatmentTypeSpinner.getSelectedItem().toString();
        String newYear = yearSpinner.getSelectedItem().toString();
        String newMonth = monthSpinner.getSelectedItem().toString();
        String newDay = daySpinner.getSelectedItem().toString();
        String newHour = hourSpinner.getSelectedItem().toString();
        String newMinute = minuteSpinner.getSelectedItem().toString();

        // 날짜 및 시간 문자열 조합
        String date = yearSpinner.getSelectedItem().toString() + "-"
                + monthSpinner.getSelectedItem().toString() + "-"
                + daySpinner.getSelectedItem().toString();

        String time = hourSpinner.getSelectedItem().toString() + ":"
                + minuteSpinner.getSelectedItem().toString();

        String newAppointmentDate = date + " " + time;

        // int 타입으로 업데이트 결과 확인
        int rowsAffected = dbHelper.updateAppointment(appointId, newAppointmentDate, newTreatmentType);

        if (rowsAffected > 0) {
            Toast.makeText(this, "예약이 성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "예약 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
