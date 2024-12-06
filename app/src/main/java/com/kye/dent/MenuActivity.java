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

public class MenuActivity extends AppCompatActivity {

    private Button appointButton, appointListButton, treatListButton, infoButton, logoutButton;
    private TextView userInfoText;
    private UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        appointButton = findViewById(R.id.appoint_button);
        appointListButton = findViewById(R.id.appoint_list_button);
        treatListButton = findViewById(R.id.treat_list_button);
        infoButton = findViewById(R.id.info_button);
        logoutButton = findViewById(R.id.logout_button);
        userInfoText = findViewById(R.id.user_info_text);

        // UserDBHelper 인스턴스 생성
        dbHelper = new UserDBHelper(this);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // 사용자 정보 가져오기
            String name = sharedPreferences.getString("name", "");
            String phone = sharedPreferences.getString("phone", "");
            String birth = sharedPreferences.getString("birth", "");

            // 디버깅 로그로 사용자 정보 확인
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

            // 디버깅 로그로 폰번호 출력
            Log.d("MenuActivity", "User name: " + name);
            Log.d("MenuActivity", "User phone: " + phone);
            Log.d("MenuActivity", "User birth: " + birth);

            // 예약하기 버튼
            appointButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // MenuActivity에서 AppointmentActivity로 이동할 때 이름과 생년월일 전달
                    Intent intent = new Intent(MenuActivity.this, AppointmentActivity.class);

                    // SharedPreferences에서 이름과 생년월일 가져오기
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    String name = sharedPreferences.getString("name", "");
                    String birth = sharedPreferences.getString("birth", "");

                    // Intent에 데이터 추가
                    intent.putExtra("userName", name);
                    intent.putExtra("userBirth", birth);

                    // AppointmentActivity로 이동
                    startActivity(intent);

                }
            });

            // 예약 내역 조회 버튼
            appointListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // MenuActivity에서 AppointmentActivity로 이동할 때 이름과 생년월일 전달
                    Intent intent = new Intent(MenuActivity.this, AppointmentListActivity.class);

                    // SharedPreferences에서 이름과 생년월일 가져오기
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    String name = sharedPreferences.getString("name", "");
                    String birth = sharedPreferences.getString("birth", "");

                    // Intent에 데이터 추가
                    intent.putExtra("userName", name);
                    intent.putExtra("userBirth", birth);

                    // AppointmentActivity로 이동
                    startActivity(intent);

                }
            });

            // 정보 버튼 클릭 시
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // SharedPreferences에서 폰번호 값 가져오기
                    if (!phone.isEmpty()) {
                        // 폰번호를 Intent로 전달하여 InfoActivity로 이동
                        Intent intent = new Intent(MenuActivity.this, InfoActivity.class);
                        intent.putExtra("userPhone", phone);  // phone 전달
                        startActivity(intent);
                    } else {
                        Toast.makeText(MenuActivity.this, "사용자 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // 로그인 정보가 없으면 로그인 화면으로 이동
            Toast.makeText(this, "로그인 정보가 없습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        }
    }
}
