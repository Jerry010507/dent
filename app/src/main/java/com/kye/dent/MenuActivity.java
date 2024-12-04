package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class MenuActivity extends AppCompatActivity {

    private Button appointButton, appointListButton, treatListButton, infoButton, logoutButton;
    private TextView userInfoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        appointButton = findViewById(R.id.appoint_button);
        appointListButton = findViewById(R.id.appoint_list_button);
        treatListButton = findViewById(R.id.treat_list_button);
        infoButton = findViewById(R.id.info_button);
        logoutButton = findViewById(R.id.logout_button);
        userInfoText = findViewById(R.id.user_info_text);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // 디버깅 로그로 SharedPreferences 값 확인
        Log.d("MenuActivity", "isLoggedIn: " + isLoggedIn);

        if (isLoggedIn) {
            // 사용자 정보 가져오기
            String name = sharedPreferences.getString("name", "");
            String phone = sharedPreferences.getString("phone", "");
            String birth = sharedPreferences.getString("birth", "");

            // 디버깅 로그로 사용자 정보 확인
            Log.d("MenuActivity", "name: " + name);
            Log.d("MenuActivity", "phone: " + phone);
            Log.d("MenuActivity", "birth: " + birth);

            // 사용자 정보 표시
            userInfoText.setText("환영합니다, " + name + "님!");

            // 로그아웃 버튼 클릭 리스너
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // SharedPreferences에서 로그인 상태를 false로 설정
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();

                    // 로그아웃 후 로그인 화면으로 이동
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 현재 액티비티 종료
                }
            });

        } else {
            // 로그인 정보가 없으면 로그인 화면으로 이동
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        }

        // 예약하기 버튼 클릭 시
        appointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);

                // SharedPreferences에서 사용자 정보 가져오기
                String name = sharedPreferences.getString("name", "");
                String phone = sharedPreferences.getString("phone", "");
                String birth = sharedPreferences.getString("birth", "");

                // Intent에 사용자 정보 추가
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("birth", birth);

                startActivity(intent);
            }
        });

        // 예약 내역 버튼 클릭 시
        appointListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppointmentListActivity.class);
                startActivity(intent);
            }
        });

        // 복약 관리 버튼 클릭 시
        treatListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TreatmentListActivity.class);
                startActivity(intent);
            }
        });

        // 개인정보 버튼 클릭 시
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
            }
        });
   }
}
