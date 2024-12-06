package com.kye.dent;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentDetailActivity extends AppointmentActivity {

    private TextView nameText, birthText, categoryText, dateTimeText;
    private Button closeButton;
    private AppointmentDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        // XML에서 각 TextView와 Button 연결
        nameText = findViewById(R.id.name_text);
        birthText = findViewById(R.id.birth_text);
        categoryText = findViewById(R.id.category_text);
        dateTimeText = findViewById(R.id.date_time_text);
        closeButton = findViewById(R.id.cancel_appointment_button);

        dbHelper = new AppointmentDBHelper(this);

        // 예약 정보를 가져와서 각 TextView에 표시
        Cursor cursor = dbHelper.getReadableDatabase().query(
                AppointmentDBHelper.TABLE_APPOINTMENT,
                null, null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // 예약 정보 가져오기
            String name = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentDBHelper.COLUMN_NAME));
            String birth = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentDBHelper.COLUMN_BIRTH_DATE));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentDBHelper.COLUMN_TREATMENT_TYPE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(AppointmentDBHelper.COLUMN_APPOINTMENT_DATE));

            // TextView에 정보 설정
            nameText.setText(name);
            birthText.setText(birth);
            categoryText.setText(category);
            dateTimeText.setText(date);

            cursor.close();
        } else {
            Toast.makeText(this, "예약 정보가 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 닫기 버튼 클릭 시
        closeButton.setOnClickListener(view -> finish());
    }
}
