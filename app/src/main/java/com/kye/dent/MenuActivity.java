package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;



public class MenuActivity extends AppCompatActivity {

    private Button make_appointment_button, check_appointment_history_button, check_treatment_history_button, personal_info_button;
    private TextView userInfoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        make_appointment_button = findViewById(R.id.make_appointment_button);
        check_appointment_history_button = findViewById(R.id.check_appointment_history_button);
        check_treatment_history_button = findViewById(R.id.check_treatment_history_button);
        personal_info_button = findViewById(R.id.personal_info_button);
        userInfoText = findViewById(R.id.user_info_text); // 사용자 정보를 표시할 TextView

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String name = sharedPreferences.getString("name", "");    // 이름 가져오기
            String phone = sharedPreferences.getString("phone", ""); // 전화번호 가져오기
            String birth = sharedPreferences.getString("birth", ""); // 생년월일 가져오기

            // 사용자 정보 표시
            userInfoText.setText("환영합니다, " + name + "님!");

        } else {
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        // 예약하기 버튼 클릭 시
        make_appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MakeAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // 예약 내역 버튼 클릭 시
        check_appointment_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckAppointmentHistoryActivity.class);
                startActivity(intent);
            }
        });

        // 복약 관리 버튼 클릭 시
        check_treatment_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckTreatmentHistoryActivity.class);
                startActivity(intent);
            }
        });

        // 개인정보 버튼 클릭 시
        personal_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
