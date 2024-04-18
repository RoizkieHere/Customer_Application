package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        LinearLayoutCompat container = findViewById(R.id.container);
        View send_email = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_email, container, false);
        View send_otp = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_otp, container, false);

        container.addView(send_email);

        //VIEW: send_email:
        AppCompatButton next_button = send_email.findViewById(R.id.next_button);
        AppCompatTextView error_msg_email = send_email.findViewById(R.id.error_msg_email);
        EditText email = send_email.findViewById(R.id.email_address);


        //VIEW: send_otp:
        AppCompatButton submit_button = send_otp.findViewById(R.id.submit_button);
        AppCompatTextView error_msg_otp = send_otp.findViewById(R.id.error_msg_otp);
        AppCompatEditText otp= send_otp.findViewById(R.id.otp);

        next_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()){
                    error_msg_email.setVisibility(View.VISIBLE);
                    email.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else {
                    error_msg_email.setVisibility(View.GONE);
                    email.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    container.removeView(send_email);
                    container.addView(send_otp);
                }

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });









    }
}