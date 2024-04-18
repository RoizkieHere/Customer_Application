package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    String otpString, shared_username;

    AppCompatTextView error_msg_otp, error_msg_form;
    EditText otp, new_password, new_password_confirm;

    AppCompatButton change_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        LinearLayoutCompat container = findViewById(R.id.container);
        View send_email = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_email, container, false);
        View send_otp = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_otp, container, false);
        View form = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_form, container, false);

        container.addView(send_email);

        //VIEW: send_email:
        AppCompatButton next_button = send_email.findViewById(R.id.next_button);
        AppCompatTextView error_msg_email = send_email.findViewById(R.id.error_msg_email);
        EditText email = send_email.findViewById(R.id.email_address);


        //VIEW: send_otp:
        AppCompatButton submit_button = send_otp.findViewById(R.id.submit_button);
        error_msg_otp = send_otp.findViewById(R.id.error_msg_otp);
        otp= send_otp.findViewById(R.id.otp);

        //VIEW: form:
        change_password = form.findViewById(R.id.change_password);
        error_msg_form = form.findViewById(R.id.error_msg_form);
        new_password = form.findViewById(R.id.new_password);
        new_password_confirm = form.findViewById(R.id.new_password_confirm);



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

                    int random_num = 100000  + (int)(Math.random() * ((999999 - 100000) + 1));
                    otpString = Integer.toString(random_num);
                    container.addView(send_otp);
                }

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (otp.getText().toString().isEmpty()) {

                    error_msg_otp.setVisibility(View.VISIBLE);
                    otp.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));

                } else {
                    error_msg_otp.setVisibility(View.GONE);
                    otp.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    container.removeView(send_otp);
                    container.addView(form);
                }

                if (!otp.getText().toString().equals(otpString)) {
                    error_msg_otp.setVisibility(View.VISIBLE);
                    error_msg_otp.setText("Otp not matched. Make sure you've entered the right pin.");
                    otp.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else {
                    error_msg_otp.setVisibility(View.GONE);
                    otp.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    container.removeView(send_otp);
                    container.addView(form);
                }

            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (!new_password.getText().toString().equals(new_password_confirm.getText().toString())){
                    error_msg_form.setVisibility(View.VISIBLE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error_msg_form.setText("Passwords don't match!");
                } else {
                    error_msg_form.setVisibility(View.GONE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    change_it();
                }

                if (new_password.getText().toString().isEmpty()){
                    error_msg_form.setVisibility(View.VISIBLE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else {
                    error_msg_form.setVisibility(View.GONE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    change_it();
                }
            }
        });
    }

    private void change_it(){

        String url = "link";

        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", "Abrera.123");
                return params;
            }
        };
        queue.add(sr);

    }
}