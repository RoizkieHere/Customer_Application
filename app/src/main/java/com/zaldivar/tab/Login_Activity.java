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

    public Button login, sign_up;
    public EditText username, password;
    public TextView error_msg, forgot_password;
    public Intent to_main;
    public String usernameString, passwordString;
    public SharedPreferences sharedPreferences;
    public String url = "https://zaldivarservices.com/android_new/customer_app/account/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_up = findViewById(R.id.sign_up);
        forgot_password.findViewById(R.id.forgot_password);

        error_msg = findViewById(R.id.error_message);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_Activity.this, Signup_Activity.class));

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       error_msg.setVisibility(View.GONE);

        to_main = new Intent(Login_Activity.this, MainActivity.class);

        login = findViewById(R.id.login_button);
        sign_up = findViewById(R.id.sign_up);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        error_msg = findViewById(R.id.error_message);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameString = username.getText().toString();
                passwordString = username.getText().toString();

                if(usernameString.isEmpty() || passwordString.isEmpty()){
                    error_msg.setVisibility(View.VISIBLE);
                    error_msg.setText("Input fields are empty!");
                } else {
                    validate_input();
                }

            }
        });
    }

    private void validate_input() {
        RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("Reject")) {

                    error_msg.setVisibility(View.VISIBLE);

                } else if (response.equals("Not Existing")) {

                    error_msg.setVisibility(View.VISIBLE);
                    error_msg.setText("Not existing. Sign-up instead!");

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