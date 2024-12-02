package com.kye.dent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;



public class SignUpActivity extends AppCompatActivity {

    private Button sign_up_button, not_sign_up_button;
    private EditText birthInput, phoneInput, nameInput;
    private UserDBHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        sign_up_button = findViewById(R.id.sign_up_button);
        not_sign_up_button = findViewById(R.id.not_sign_up_button);
        nameInput = findViewById(R.id.name);
        birthInput = findViewById(R.id.birth);
        phoneInput = findViewById(R.id.phone);
        dbhelper = new UserDBHelper(this);

        // 초진 등록 이벤트
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String birth = birthInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();

                if(name.isEmpty() || birth.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDTO newUser = new UserDTO(name, birth, phone);
                dbhelper.insertUser(newUser);
                Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 취소 버튼 클릭 시
        not_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


