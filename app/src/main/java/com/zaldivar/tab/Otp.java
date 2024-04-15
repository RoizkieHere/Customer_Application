package com.zaldivar.tab;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Intent get_it = getIntent();
        Bundle b = get_it.getExtras();

        AppCompatEditText otp_editText = findViewById(R.id.otp);

        String otp_here = otp_editText.getText().toString();

        if(otp_here.equals(b.get("otp"))){
            
        }














    }
}