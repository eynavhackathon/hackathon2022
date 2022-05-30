package com.app.eynav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button btn_new_immigrant, btn_native_b, btn_volunteer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findView();
        onClick();
    }

    private void onClick() {
        btn_new_immigrant.setOnClickListener(l ->{
            Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
            intent.putExtra("userType","new_immigrant");
            startActivity(intent);
        });
        btn_native_b.setOnClickListener(l ->{
            Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
            intent.putExtra("userType","native_b");
            startActivity(intent);
        });
        btn_volunteer.setOnClickListener(l ->{
            Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
            intent.putExtra("userType","volunteer");
            startActivity(intent);
        });
    }

    private void findView() {
        btn_new_immigrant = findViewById(R.id.btn_new_immigrant);
        btn_native_b = findViewById(R.id.btn_native_b);
        btn_volunteer = findViewById(R.id.btn_volunteer);
    }
}