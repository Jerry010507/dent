package com.kye.dent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReturnVisitActivity extends AppCompatActivity {

    private Button returnVisitButton, cancelButton;
    private EditText birthInput, phoneInput, nameInput;
    private UserDBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_visit);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        returnVisitButton = findViewById(R.id.return_visit_button);
        cancelButton = findViewById(R.id.cancel_button);
        nameInput = findViewById(R.id.name);
        birthInput = findViewById(R.id.birth);
        phoneInput = findViewById(R.id.phone);
        dbhelper = new UserDBHelper(this);

        // 재진 등록 이벤트
        returnVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String birth = birthInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                if (name.isEmpty() || birth.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(ReturnVisitActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 사용자 확인 및 PID 가져오기
                int pid = dbhelper.getUserPID(name, birth, phone);
                if (pid != -1) { // 사용자 존재 확인
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putInt("pid", pid); // PID 저장
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.putString("birth", birth);
                    editor.apply();

                    Toast.makeText(ReturnVisitActivity.this, "재진 로그인 성공", Toast.LENGTH_SHORT).show();

                    // MenuActivity로 이동
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ReturnVisitActivity.this, "일치하지 않는 정보입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 취소 버튼 클릭 시
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


