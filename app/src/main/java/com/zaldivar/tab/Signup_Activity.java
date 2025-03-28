package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
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
import java.util.Random;

public class Signup_Activity extends AppCompatActivity {

    String otp, emailString;

    Boolean email_exists;

    AppCompatTextView error;
    EditText email_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        error = findViewById(R.id.error_message);

        AppCompatButton send_otp = findViewById(R.id.send_otp);
        email_add = findViewById(R.id.email_address);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this function is called before text is edited
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // this function is called when text is edited
                check_email();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
            }
        };

        email_add.addTextChangedListener(textWatcher);


        send_otp.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (email_add.getText().toString().isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Please fill out empty field!");
                    email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else {
                    send_otp();
                }


            }
        });

    }

    private void check_email() {

        String url1 = "https://zaldivarservices.com/android_new/customer_app/account/check_email.php";

        RequestQueue queue = Volley.newRequestQueue(Signup_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {

                if (email_add.getText().toString().isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Please fill out empty field!");
                    email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else

                    if (response.equals("1")) {
                        email_exists = true;
                        error.setVisibility(View.VISIBLE);
                        email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                        error.setText("Email exists!");
                    } else if (response.equals("0")) {
                        email_exists = false;
                        error.setVisibility(View.GONE);
                        email_add.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email_add.getText().toString());
                return params;

            }
        };
        queue.add(sr);
    }

    private void send_otp() {

        String url1 = "https://zaldivarservices.com/android_new/customer_app/account/check_email.php";

        RequestQueue queue = Volley.newRequestQueue(Signup_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {

                if (email_add.getText().toString().isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Please fill out empty field!");
                    email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else

                if (response.equals("1")) {
                    email_exists = true;
                    error.setVisibility(View.VISIBLE);
                    email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error.setText("Email exists!");
                } else if (response.equals("0")) {
                    email_exists = false;
                    error.setVisibility(View.GONE);
                    email_add.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    Intent to_otp = new Intent(Signup_Activity.this, Otp.class);
                    to_otp.putExtra("email", email_add.getText().toString());
                    startActivity(to_otp);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email_add.getText().toString());
                return params;

            }
        };
        queue.add(sr);
    }



}