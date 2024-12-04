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
    private UserDBHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_visit);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        firstVisitButton = findViewById(R.id.first_visit_button);
        cancelButton = findViewById(R.id.cancel_button);
        nameInput = findViewById(R.id.name);
        birthInput = findViewById(R.id.birth);
        phoneInput = findViewById(R.id.phone);
        dbhelper = new UserDBHelper(this);

        // 초진 등록 이벤트
        firstVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String birth = birthInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                if(name.isEmpty() || birth.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(FirstVisitActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDTO newUser = new UserDTO(name, birth, phone);
                dbhelper.insertUser(newUser);
                // 로그인 정보 SharedPreferences에 저장
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("name", name);
                editor.putString("birth", birth);
                editor.putString("phone", phone);
                editor.apply(); // 변경 사항 저장

                Toast.makeText(FirstVisitActivity.this, "초진 접수 성공", Toast.LENGTH_SHORT).show();

                // MenuActivity로 이동
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
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
