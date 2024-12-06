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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // UI 요소 초기화
        initUI();

        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);

        // 로그인 상태 확인
        if (isUserLoggedIn()) {
            displayUserInfo();
            setButtonListeners();
        } else {
            redirectToLogin();
        }
    }

    // UI 요소 초기화 메서드
    private void initUI() {
        appointButton = findViewById(R.id.appoint_button);
        appointListButton = findViewById(R.id.appoint_list_button);
        treatListButton = findViewById(R.id.treat_list_button);
        infoButton = findViewById(R.id.info_button);
        logoutButton = findViewById(R.id.logout_button);
        userInfoText = findViewById(R.id.user_info_text);
        dbHelper = new UserDBHelper(this);
    }

    // 로그인 상태 확인 메서드
    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // 사용자 정보 표시 메서드
    private void displayUserInfo() {
        String name = sharedPreferences.getString("name", "");
        userInfoText.setText("환영합니다, " + name + "님!");
        Log.d("MenuActivity", "User Info: " + getUserInfo());
    }

    // 사용자 정보 가져오기
    private String getUserInfo() {
        String name = sharedPreferences.getString("name", "");
        String phone = sharedPreferences.getString("phone", "");
        String birth = sharedPreferences.getString("birth", "");
        return "Name: " + name + ", Phone: " + phone + ", Birth: " + birth;
    }

    // 버튼 클릭 리스너 설정 메서드
    private void setButtonListeners() {
        logoutButton.setOnClickListener(this::logout);
        appointButton.setOnClickListener(this::goToAppointmentActivity);
        appointListButton.setOnClickListener(this::goToAppointmentListActivity);
        infoButton.setOnClickListener(this::goToInfoActivity);
    }

    // 로그아웃 처리 메서드
    private void logout(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        if (editor.commit()) {
            Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        } else {
            Toast.makeText(this, "로그아웃 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    // 로그인 화면으로 이동
    private void redirectToLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // AppointmentActivity로 이동
    private void goToAppointmentActivity(View view) {
        Intent intent = new Intent(this, AppointmentActivity.class);
        intent.putExtra("userName", sharedPreferences.getString("name", ""));
        intent.putExtra("userBirth", sharedPreferences.getString("birth", ""));
        startActivity(intent);
    }

    // AppointmentListActivity로 이동
    private void goToAppointmentListActivity(View view) {
        Intent intent = new Intent(this, AppointmentListActivity.class);
        intent.putExtra("userName", sharedPreferences.getString("name", ""));
        intent.putExtra("userBirth", sharedPreferences.getString("birth", ""));
        intent.putExtra("userPhone", sharedPreferences.getString("phone", ""));
        startActivity(intent);
    }

    // InfoActivity로 이동
    private void goToInfoActivity(View view) {
        String phone = sharedPreferences.getString("phone", "");
        if (!phone.isEmpty()) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("userPhone", phone);
            startActivity(intent);
        } else {
            Toast.makeText(this, "사용자 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
