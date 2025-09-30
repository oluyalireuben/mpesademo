package com.example.mpesademo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpesademo.db.DBHelper;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SendMpesaActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtAmount;
    Button btnSendMoney, btnPochi;
    DBHelper dbHelper;
    static double balance = 50000.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mpesa);

        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtAmount = findViewById(R.id.edt_amount);
        btnSendMoney = findViewById(R.id.btn_send_money);
        btnPochi = findViewById(R.id.btn_pochi);

        dbHelper = new DBHelper(this);

        // ✅ Send Money flow
        btnSendMoney.setOnClickListener(v -> processTransaction(true));

        // ✅ Pochi la Biashara flow
        btnPochi.setOnClickListener(v -> processTransaction(false));
    }

    private void processTransaction(boolean includePhone) {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String amountStr = edtAmount.getText().toString().trim();

        if (name.isEmpty() || amountStr.isEmpty() || (includePhone && phone.isEmpty())) {
            edtAmount.setError("Please fill all required fields");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // Deduct from balance
        balance -= amount;
        if (balance < 0) balance = 50000.00;

        // Format money values with commas
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        String amountFormatted = nf.format(amount);
        String balanceFormatted = nf.format(balance);

        // Generate transaction code (always uppercase)
        String code = generateCode();

        // Date & time
        String time = new SimpleDateFormat("d/M/yyyy 'at' h:mm a", Locale.getDefault()).format(new Date());

        // ✅ Build message differently based on transaction type
        String message;
        if (includePhone) {
            // Send Money message
            message = code + " Confirmed. Ksh" + amountFormatted + " sent to " + name + " " + phone
                    + " on " + time + ". New M-PESA balance is Ksh" + balanceFormatted
                    + ". Transaction cost, Ksh0.00. Amount you can transact within the day is 499,950.00. "
                    + "Sign up for Lipa Na M-PESA Till online https://m-pesaforbusiness.co.ke";
        } else {
            // Pochi la Biashara message (no phone)
            message = code + " Confirmed. Ksh" + amountFormatted + " sent to " + name
                    + " on " + time + ". New M-PESA balance is Ksh" + balanceFormatted
                    + ". Transaction cost, Ksh0.00. Amount you can transact within the day is 499,950.00. "
                    + "Sign up for Lipa Na M-PESA Till online https://m-pesaforbusiness.co.ke";
        }

        dbHelper.insertMessage(message);

        // Go back to messages screen
        startActivity(new Intent(this, MpesaMessagesActivity.class));
        finish();
    }

    /**
     * Generate yearly M-PESA transaction code
     * Example (2025): TIUDU627T7
     * Always 10 characters, uppercase letters + numbers
     */
    private String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("T"); // start with T
        Random r = new Random();

        for (int i = 0; i < 9; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }

        return sb.toString().toUpperCase(Locale.ROOT);
    }
}
