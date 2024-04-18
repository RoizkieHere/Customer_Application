package com.zaldivar.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupForm extends AppCompatActivity {

    public String usernameString,
                passwordString,
                confirmString,
                firstname_string,
                lastname_string,
                phone_string,
                address_string;


    public View success, login_credentials, personal_info;

    public LinearLayoutCompat container;

    public boolean username_exists;

    public AppCompatTextView sign_up_err;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);


        container = findViewById(R.id.container);

        personal_info = LayoutInflater.from(SignupForm.this).inflate(R.layout.personal_information, container, false);
        login_credentials = LayoutInflater.from(SignupForm.this).inflate(R.layout.login_credentials, container, false);
        success = LayoutInflater.from(SignupForm.this).inflate(R.layout.success_message, container, false);


        //For personal info:
        AppCompatButton next_button = personal_info.findViewById(R.id.next_button);
        EditText firstname, lastname, address, phone_number;
        firstname = personal_info.findViewById(R.id.firstname);
        lastname = personal_info.findViewById(R.id.lastname);
        address = personal_info.findViewById(R.id.address);
        phone_number = personal_info.findViewById(R.id.phone_num);
        AppCompatTextView pe_error_msg = personal_info.findViewById(R.id.error_msg);

        //For login_credentials:
        AppCompatButton sign_up_button = login_credentials.findViewById(R.id.sign_up_button);
        AppCompatButton back_button = login_credentials.findViewById(R.id.prev_button);
        EditText username, password, password_confirm;
        username = login_credentials.findViewById(R.id.username);
        password = login_credentials.findViewById(R.id.password);
        password_confirm = login_credentials.findViewById(R.id.password_confirm);
        sign_up_err = login_credentials.findViewById(R.id.sign_up_err);

        container.addView(personal_info);

        //Third View:
        AppCompatButton to_login_page = success.findViewById(R.id.to_login_page);

        to_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_login_page = new Intent(SignupForm.this, Login_Activity.class);
                startActivity(to_login_page);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this function is called before text is edited
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // this function is called when text is edited
                String check_it = username.getText().toString();
                check_username(check_it, username);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
            }
        };

        username.addTextChangedListener(textWatcher);


        next_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {


                firstname_string = firstname.getText().toString();
                lastname_string = lastname.getText().toString();
                phone_string = phone_number.getText().toString();
                address_string = address.getText().toString();


                if(firstname_string.isEmpty() || lastname_string.isEmpty() || phone_string.isEmpty() || address_string.isEmpty()){

                    pe_error_msg.setVisibility(View.VISIBLE);
                    pe_error_msg.setText("Please fill-out empty field/s.");

                    if(firstname_string.isEmpty()){
                        firstname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        firstname.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if(lastname_string.isEmpty()){
                        lastname.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        lastname.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if(address_string.isEmpty()) {
                        address.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        address.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if(phone_string.isEmpty()) {
                        phone_number.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        phone_number.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }



                } else {
                    pe_error_msg.setVisibility(View.GONE);
                    firstname.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    lastname.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    address.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    phone_number.setBackground(getResources().getDrawable(R.drawable.input_field, null));


                    container.removeView(personal_info);
                    container.addView(login_credentials);
                }
            }
        });




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(login_credentials);
                container.addView(personal_info);
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                usernameString = username.getText().toString();
                passwordString = password.getText().toString();
                confirmString = password_confirm.getText().toString();



                if (usernameString.isEmpty() || passwordString.isEmpty() || confirmString.isEmpty()) {

                    sign_up_err.setVisibility(View.VISIBLE);
                    sign_up_err.setText("Please fill out empty field/s.");

                    if (usernameString.isEmpty()) {
                        username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if (passwordString.isEmpty()) {
                        password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                    if (confirmString.isEmpty()) {
                        password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }

                }  else {

                    if(username_exists == true){
                        username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                    } else {
                        username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }


                    if (passwordString.equals(confirmString)){
                        sign_up_err.setVisibility(View.VISIBLE);
                        password.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                        password_confirm.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));
                        sign_up_err.setText("Passwords don't match!");

                    } else {
                        password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                        password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    }


                    sign_up_err.setVisibility(View.GONE);
                    username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    password.setBackground(getResources().getDrawable(R.drawable.input_field, null));
                    password_confirm.setBackground(getResources().getDrawable(R.drawable.input_field, null));

                    signup();
                }



            }
        });

    }



    private void signup(){
        String url = "https://zaldivarservices.com/android_new/customer_app/account/sign_up.php";

        RequestQueue queue = Volley.newRequestQueue(SignupForm.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Success")){
                    container.removeView(login_credentials);
                    container.addView(success);
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
                params.put("firstname", firstname_string);
                params.put("lastname", lastname_string);
                params.put("phone_number", phone_string);
                params.put("address", address_string);
                params.put("password", passwordString);
                return params;

            }
        };
        queue.add(sr);
    }

    private void check_username(String check, EditText username){

        String url = "https://zaldivarservices.com/android_new/customer_app/account/check_username.php";

        RequestQueue queue = Volley.newRequestQueue(SignupForm.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(String response) {

                if(response.equals("1")){
                    username_exists = true;
                    sign_up_err.setVisibility(View.VISIBLE);
                    username.setBackground(getResources().getDrawable(R.drawable.error_input_field, null));;
                    sign_up_err.setText("Username exists. Please construct a unique username");
                } else {
                    username_exists = false;
                    sign_up_err.setVisibility(View.GONE);
                    username.setBackground(getResources().getDrawable(R.drawable.input_field, null));
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
                params.put("username", check);
                return params;

            }
        };
        queue.add(sr);

    }



}