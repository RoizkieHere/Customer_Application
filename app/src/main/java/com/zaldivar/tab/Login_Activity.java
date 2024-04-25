package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
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

public class Login_Activity extends AppCompatActivity {

    Intent to_main;

    TextView error_msg;
    String usernameString, passwordString;

    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton sign_up_me = findViewById(R.id.sign_up);
        TextView forgot_password1 = findViewById(R.id.forgot_password1);

        error_msg = findViewById(R.id.error_message);

        sign_up_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_Activity.this, Signup_Activity.class));

            }
        });

        forgot_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_recovery_page = new Intent(Login_Activity.this, ForgotPassword.class);
                startActivity(to_recovery_page);
            }
        });

       error_msg.setVisibility(View.GONE);

        to_main = new Intent(Login_Activity.this, MainActivity.class);

        AppCompatButton login = findViewById(R.id.login_button);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        error_msg = findViewById(R.id.error_message);

        login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                usernameString = username.getText().toString();
                passwordString = password.getText().toString();

                if(usernameString.isEmpty() || passwordString.isEmpty()){
                    error_msg.setVisibility(View.VISIBLE);
                    error_msg.setText("Please fill-out empty field/s!");

                    if(passwordString.isEmpty()){
                        password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if(usernameString.isEmpty()){
                        username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                } else {
                    error_msg.setVisibility(View.GONE);
                    password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    validate_input();
                }

            }
        });
    }

    private void validate_input() {
        String url = "https://zaldivarservices.com/android_new/customer_app/account/login.php";

        RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {


                if (response.equals("Reject")) {

                    error_msg.setVisibility(View.VISIBLE);
                    password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error_msg.setText("Wrong password.");

                } else if (response.equals("Not Existing")) {

                    error_msg.setVisibility(View.VISIBLE);
                    password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    error_msg.setText("Not existing. Sign-up instead!");

                } else {
                    error_msg.setVisibility(View.GONE);

                    password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    username.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    SharedPreferences sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String[] username_and_email = response.split(";");

                    editor.putString("email", username_and_email[0]);
                    editor.putString("user", username_and_email[1]);
                    editor.putString("name", username_and_email[2]);
                    editor.apply();

                    Intent login = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(login);

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
                params.put("username", usernameString);
                params.put("password", passwordString);
                return params;
            }
        };
        queue.add(sr);

    }

}