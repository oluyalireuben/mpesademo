package com.example.mpesademo;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mpesademo.adapter.MessageAdapter;
import com.example.mpesademo.db.DBHelper;
import com.example.mpesademo.model.Message;

import java.util.List;

public class MpesaMessagesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageAdapter adapter;
    DBHelper dbHelper;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa_messages);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_messages);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());

        List<Message> messages = dbHelper.getAllMessages();
        adapter = new MessageAdapter(this, messages, recyclerView); // pass context + recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
