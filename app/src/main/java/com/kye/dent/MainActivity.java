package com.kye.dent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button first_visit_button = findViewById(R.id.first_visit_button);
        Button return_visit_button = findViewById(R.id.return_visit_button);

        // 초진 버튼 클릭 시
        first_visit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirstVisitActivity.class);
                startActivity(intent);
            }
        });

        // 재진 버튼 클릭 시
        return_visit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReturnVisitActivity.class);
                startActivity(intent);
            }
        });
    }
}