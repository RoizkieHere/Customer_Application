package com.zaldivar.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PasswordChanged extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_changed);

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");

        AppCompatButton sign_in_page = findViewById(R.id.back_to_login);

        if (from.equals("0")){
            sign_in_page.setText("Back");
        } else {
            sign_in_page.setText("Sign in");
        }

        sign_in_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from.equals("0")){
                    Intent intent = new Intent(PasswordChanged.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PasswordChanged.this, Login_Activity.class);
                    startActivity(intent);
                }



            }
        });

    }
}