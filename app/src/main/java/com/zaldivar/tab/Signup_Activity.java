package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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

    String url = "https://zaldivarservices.com/android_new/customer_app/account/send_otp.php";

    String otp;

    Boolean email_exists;

    TextView err;

    EditText email_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        AppCompatButton sign_up = findViewById(R.id.sign_up);
        email_add = findViewById(R.id.email_address);

        err.findViewById(R.id.error_msg);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this function is called before text is edited
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // this function is called when text is edited
                String check_it = email_add.getText().toString();
                check_email(check_it);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
            }
        };

        email_add.addTextChangedListener(textWatcher);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (email_exists = false){

                    err.setVisibility(View.GONE);
                    email_add.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    int random_num = 100000  + (int)(Math.random() * ((999999 - 100000) + 1));

                    otp = Integer.toString(random_num);

                    Intent to_otp = new Intent(Signup_Activity.this, Otp.class);
                    to_otp.putExtra("otp", otp);
                    startActivity(to_otp);

                    send_otp();
                }


            }
        });

    }

    private void check_email(String check) {

        String url = "https://zaldivarservices.com/android_new/customer_app/account/check_email.php";

        RequestQueue queue = Volley.newRequestQueue(Signup_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {

                if (response.equals("1")) {
                    email_exists = true;
                    err.setVisibility(View.VISIBLE);
                    email_add.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    err.setText("Email exists. Recover your account instead!");
                } else {
                    email_exists = false;
                    err.setVisibility(View.GONE);
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
                params.put("email", check);
                return params;

            }
        };
        queue.add(sr);
    }

    private void send_otp() {

        String emailString = email_add.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(Signup_Activity.this);
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
                params.put("receiver", emailString);
                params.put("otp", otp);
                return params;
            }
        };
        queue.add(sr);

    }



}