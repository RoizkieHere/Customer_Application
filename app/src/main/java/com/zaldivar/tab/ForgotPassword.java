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

    EditText email;

    LinearLayoutCompat container;

    View send_email, send_otp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        container = findViewById(R.id.container);
        send_email = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_email, container, false);
        send_otp = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_otp, container, false);
        View form = LayoutInflater.from(ForgotPassword.this).inflate(R.layout.forgot_password_form, container, false);



        container.addView(send_email);

        //VIEW: send_email:
        AppCompatButton next_button = send_email.findViewById(R.id.next_button);
        AppCompatTextView error_msg_email = send_email.findViewById(R.id.error_msg_email);
        email = send_email.findViewById(R.id.email_address);


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

                    check_email(email.getText().toString(), error_msg_email);

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

            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (new_password.getText().toString().isEmpty()){
                    error_msg_form.setVisibility(View.VISIBLE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                } else {
                    error_msg_form.setVisibility(View.GONE);
                    new_password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    if (!new_password.getText().toString().equals(new_password_confirm.getText().toString())){
                        error_msg_form.setVisibility(View.VISIBLE);
                        new_password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                        error_msg_form.setText("Passwords don't match!");
                    } else {
                        error_msg_form.setVisibility(View.GONE);
                        new_password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                        change_it();
                    }
                }
            }
        });
    }

    private void change_it(){

        String url = "https://zaldivarservices.com/android_new/customer_app/account/change_pass.php";

        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")){
                    Intent intent = new Intent(ForgotPassword.this, PasswordChanged.class);
                    intent.putExtra("from", "1");
                    startActivity(intent);
                }


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
                params.put("email", email.getText().toString());
                params.put("password", new_password.getText().toString());
                return params;
            }
        };
        queue.add(sr);

    }

    private void check_email(String check, TextView error) {

        String url1 = "https://zaldivarservices.com/android_new/customer_app/account/check_email.php";

        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {

                if (response.equals("1")) {
                    error.setVisibility(View.VISIBLE);
                    email.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error.setText("Email exists!");

                } else if (response.equals("0")) {
                    error.setVisibility(View.GONE);
                    email.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    int random_num = 100000  + (int)(Math.random() * ((999999 - 100000) + 1));
                    otpString = Integer.toString(random_num);


                    String url1 = "https://zaldivarservices.com/android_new/customer_app/account/send_otp.php";

                    RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
                    StringRequest sr = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
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
                            params.put("receiver", email.getText().toString());
                            params.put("otp", otpString);
                            return params;
                        }
                    };
                    queue.add(sr);

                    container.removeView(send_email);
                    container.addView(send_otp);
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
}