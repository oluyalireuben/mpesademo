package com.example.mpesademo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnMessages, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMessages = findViewById(R.id.btn_messages);
        btnSend = findViewById(R.id.btn_send);

        btnMessages.setOnClickListener(v -> startActivity(new Intent(this, MpesaMessagesActivity.class)));
        btnSend.setOnClickListener(v -> startActivity(new Intent(this, SendMpesaActivity.class)));
    }
}
