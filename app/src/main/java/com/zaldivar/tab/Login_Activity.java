package com.zaldivar.tab;

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
    TextView error_msg;

    AppCompatButton login, sign_up;
    AppCompatEditText username, password;
    String url = "https://zaldivarservices.com/android_new/customer_app/account/login.php";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        error_msg.setVisibility(View.GONE);

        login = findViewById(R.id.login_button);
        sign_up = findViewById(R.id.sign_up);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        error_msg = findViewById(R.id.error_message);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_input();

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sign_up = new Intent(Login_Activity.this, Signup.class);
                startActivity(sign_up);

            }
        });


    }

    private void validate_input() {

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("Reject")) {

                    error_msg.setVisibility(View.VISIBLE);

                } else if (response.equals("Not Existing")) {

                    error_msg.setVisibility(View.VISIBLE);
                    error_msg.setText("Not existing. Please try again or Sign-up");

                } else {
                    error_msg.setVisibility(View.GONE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", usernameString);

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