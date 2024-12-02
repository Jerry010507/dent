package com.kye.dent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class CheckHistoryActivity extends AppCompatActivity {
    private Button check_appointment_history_button, check_treatment_history_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        check_appointment_history_button = findViewById(R.id.check_appointment_history_button);
        check_treatment_history_button = findViewById(R.id.check_treatment_history_button);

        // 예약 내역 확인 버튼 클릭 시
        check_appointment_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckAppointmentHistoryActivity.class);
                startActivity(intent);
            }
        });
        // 진료 내역 확인 버튼 클릭 시
        check_treatment_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckTreatmentHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}