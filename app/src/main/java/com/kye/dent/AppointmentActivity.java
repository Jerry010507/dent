package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class AppointmentActivity extends AppCompatActivity {

    private Button appointButton, cancelButton;
    private TextView nameText, birthText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        appointButton = findViewById(R.id.appoint_button);
        cancelButton = findViewById(R.id.cancel_button);
        nameText = findViewById(R.id.name);
        birthText = findViewById(R.id.birth);

        if (isLoggedIn) {
            String name = sharedPreferences.getString("name", "");    // 이름 가져오기
            String birth = sharedPreferences.getString("birth", ""); // 생년월일 가져오기

            // 사용자 정보 TextView에 설정
            nameText.setText(name);
            birthText.setText(birth);

        } else {
            // 로그인 정보가 없을 때 처리
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ReturnVisitActivity.class);
            startActivity(intent);
            finish();
        }

        Spinner categorySpinner = (Spinner)findViewById(R.id.category);
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // 확인 버튼 클릭 시
        appointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 확인 버튼 클릭 시 appointment 테이블에 데이터 자장
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        // 취소 버튼 클릭 시
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}