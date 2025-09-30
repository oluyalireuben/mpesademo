package com.example.mpesademo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpesademo.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SendMpesaActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtAmount;
    Button btnSubmit;
    DBHelper dbHelper;
    static double balance = 50000.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mpesa);

        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtAmount = findViewById(R.id.edt_amount);
        btnSubmit = findViewById(R.id.btn_submit);
        dbHelper = new DBHelper(this);

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String phone = edtPhone.getText().toString();
            double amount = Double.parseDouble(edtAmount.getText().toString());

            balance -= amount;
            if (balance < 0) balance = 50000.00;

            String code = generateCode();
            String time = new SimpleDateFormat("d/M/yyyy 'at' h:mm a", Locale.getDefault()).format(new Date());

            String message = code + " Confirmed. Ksh" + amount + " sent to " + name + " " + phone
                    + " on " + time + ". New M-PESA balance is Ksh" + String.format("%.2f", balance)
                    + ". Transaction cost, Ksh0.00. Amount you can transact within the day is 499,950.00. "
                    + "Sign up for Lipa Na M-PESA Till online https://m-pesaforbusiness.co.ke";

            dbHelper.insertMessage(message);

            startActivity(new Intent(this, MpesaMessagesActivity.class));
            finish();
        });
    }

    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
