package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    private TextView nameText, birthText, phoneText;
    private UserDBHelper dbHelper;
    private int userPID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // TextView 초기화
        nameText = findViewById(R.id.name);
        birthText = findViewById(R.id.birth);
        phoneText = findViewById(R.id.phone);

        // UserDBHelper 인스턴스 생성
        dbHelper = new UserDBHelper(this);

        // Intent에서 userPhone 가져오기
        Intent intent = getIntent();
        String userPhone = intent.getStringExtra("userPhone");

        // 디버깅 로그로 폰번호 출력
        Log.d("InfoActivity", "User Phone: " + userPhone);

        if (userPhone != null && !userPhone.isEmpty()) {
            // 폰번호로 데이터베이스에서 사용자 정보 가져오기
            UserDTO user = dbHelper.getUserByPhone(userPhone);

            if (user != null) {
                // 사용자 정보 TextView에 설정
                nameText.setText(user.getName());        // 이름 설정
                birthText.setText(user.getBirthDate());  // 생년월일 설정
                phoneText.setText(user.getPhoneNumber()); // 폰번호 설정
            } else {
                // 사용자가 존재하지 않는 경우
                Toast.makeText(this, "사용자 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();  // 현재 Activity 종료
            }
        } else {
            // 로그인 정보가 없을 경우
            Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            finish();  // 현재 Activity 종료
        }
    }
}