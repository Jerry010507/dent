package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class FirstVisitActivity extends AppCompatActivity {
    private Button firstVisitButton, cancelButton;
    private EditText birthInput, phoneInput, nameInput;
    private UserDBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_visit);

        // 위젯 초기화
        firstVisitButton = findViewById(R.id.first_visit_button);
        cancelButton = findViewById(R.id.cancel_button);
        nameInput = findViewById(R.id.name);
        birthInput = findViewById(R.id.birth);
        phoneInput = findViewById(R.id.phone);
        dbHelper = new UserDBHelper(this);

        // 초진 등록 버튼 클릭 이벤트
        firstVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String birth = birthInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                // 필수 입력 검증
                if (name.isEmpty() || birth.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(FirstVisitActivity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 중복 사용자 확인: getUserPID를 사용하여 사용자 존재 여부 확인
                int pid = dbHelper.getUserPID(name, birth, phone);
                if (pid != -1) { // PID가 -1이 아니면 이미 등록된 사용자
                    Toast.makeText(FirstVisitActivity.this, "이미 등록된 사용자입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DB에 사용자 정보 저장
                try {
                    UserDTO newUser = new UserDTO(0, name, birth, phone); // PID는 자동 증가
                    int newPid = dbHelper.insertUser(newUser); // insertUser 메서드가 PID를 반환하도록 수정

                    // DB에 사용자 정보 저장 후, PID 값을 SharedPreferences에 저장
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putInt("userPID", newPid);  // userPID 저장
                    editor.putString("name", name);    // 이름 저장
                    editor.putString("birth", birth);  // 생년월일 저장
                    editor.putString("phone", phone);  // 전화번호 저장
                    editor.apply();

                    Toast.makeText(FirstVisitActivity.this, "초진 접수 성공", Toast.LENGTH_SHORT).show();

                    // MenuActivity로 이동
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish(); // 현재 액티비티 종료
                } catch (Exception e) {
                    Toast.makeText(FirstVisitActivity.this, "데이터 저장 중 오류 발생", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        // 취소 버튼 클릭 이벤트
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메인 화면으로 이동
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });
    }
}
