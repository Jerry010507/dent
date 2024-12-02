package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class MakeAppointmentActivity extends AppCompatActivity {
    private Button make_appointment_button, cancel_appointment_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_appointment);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String name = sharedPreferences.getString("name", "");    // 이름 가져오기
            String phone = sharedPreferences.getString("phone", ""); // 전화번호 가져오기
            String birth = sharedPreferences.getString("birth", ""); // 생년월일 가져오기


        } else {
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        Spinner categorySpinner = (Spinner)findViewById(R.id.category);
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        make_appointment_button = findViewById(R.id.make_appointment_button);
        cancel_appointment_button = findViewById(R.id.cancel_appointment_button);

        // 확인 버튼 클릭 시
        make_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckDetailedAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // 취소 버튼 클릭 시
        cancel_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}