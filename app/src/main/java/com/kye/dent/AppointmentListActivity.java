package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class AppointmentListActivity extends AppCompatActivity {

    private RecyclerView appointmentRecyclerView;
    private AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        // 로그인된 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", "");
        String name = sharedPreferences.getString("name", "");
        String birth = sharedPreferences.getString("birth", "");

        // DB에서 해당 사용자의 예약 내역 가져오기
        AppointmentDBHelper dbHelper = new AppointmentDBHelper(this);
        List<AppointmentDTO> appointments = dbHelper.getAppointmentsByPhone(phone);

        // 예약 내역 출력 - appointments 리스트 확인
        if (appointments != null) {
            Log.d("AppointmentListActivity", "Appointments size: " + appointments.size());
            for (AppointmentDTO appointment : appointments) {
                Log.d("AppointmentListActivity", "Appointment details: " + appointment.getAppointmentDate() + " / " + appointment.getTreatmentType());
            }
        } else {
            Log.d("AppointmentListActivity", "No appointments found for this user.");
        }

        // RecyclerView 설정
        appointmentRecyclerView = findViewById(R.id.appointment_recycler_view);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 설정
        appointmentAdapter = new AppointmentAdapter(appointments, this::onAppointmentSelected);
        appointmentRecyclerView.setAdapter(appointmentAdapter);
    }

    private void onAppointmentSelected(AppointmentDTO appointment) {
        // 예약 상세 정보 보기
        Intent intent = new Intent(this, AppointmentDetailActivity.class);
        intent.putExtra("appointId", appointment.getAppointId());
        startActivity(intent);
    }
}
