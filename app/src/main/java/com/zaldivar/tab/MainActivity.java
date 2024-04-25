package com.zaldivar.tab;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    String email_address, usernameString, reference, date;

    SharedPreferences sharedPreferences;

    ImageView settings;

    String generated_reference;

    EditText amount;

    Dialog confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);

        if(!sharedPreferences.contains("user")){
            Intent supplier = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(supplier);
        }

        amount = findViewById(R.id.amount);
        Button send_order = findViewById(R.id.send_order);

        confirmation =  new Dialog(MainActivity.this);


        send_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch_latest_ref();

                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "It is empty. Please fill it out with the desired quantity.", Toast.LENGTH_SHORT).show();
                } else {

                    confirmation.setContentView(R.layout.confirm_order_dialog);
                    confirmation.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    confirmation.setCancelable(false);
                    confirmation.getWindow().getAttributes().windowAnimations = R.style.animation;

                    Button yes = confirmation.findViewById(R.id.yes);
                    Button no = confirmation.findViewById(R.id.no);
                    TextView question =  confirmation.findViewById(R.id.question);

                    double amount_int = Double.parseDouble(amount.getText().toString());


                    if (amount_int <= 1.0){
                        question.setText("Are you sure you want to order ".concat(amount.getText().toString()).concat( " ton of copra?"));
                    } else if (amount_int > 1.0) {
                        question.setText("Are you sure you want to order ".concat(amount.getText().toString()).concat( " tons of copra?"));
                    }

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            send_now();

                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmation.dismiss();

                        }
                    });

                    confirmation.show();


                }

            }
        });

        email_address = sharedPreferences.getString("email", "");
        usernameString = sharedPreferences.getString("user", "");

        //Dashboard:
        TextView username_txt = findViewById(R.id.username);
        TextView email_add_txt = findViewById(R.id.email_address);

        username_txt.setText(usernameString);
        email_add_txt.setText(email_address);


        tabLayout = findViewById(R.id.tablayout);
        viewPager =  findViewById(R.id.viewpager);
        settings = findViewById(R.id.settings);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new Pending(), "Pending");
        vpAdapter.addFragment(new Transit(), "On Delivery");
        vpAdapter.addFragment(new Completed(), "Completed");
        vpAdapter.addFragment(new Cancelled(), "Cancelled");
        RequestQueue requestQueue;


        viewPager.setAdapter(vpAdapter);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(v);

            }
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_settings);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.logout) {

                    // Clear values of sharedpreferences:
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);

                    return true;
                } else if (item.getItemId() == R.id.change_password){

                    Intent to_forgot = new Intent(MainActivity.this, Change.class);
                    to_forgot.putExtra("username", usernameString);
                    startActivity(to_forgot);

                    return  true;
                } else if (item.getItemId() ==  R.id.change_profile) {

                    Intent to_profile = new Intent(MainActivity.this, Profile.class);
                    startActivity(to_profile);


                    return  true;

                } else {
                    return false;
                }
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Dismiss the popup menu
                menu.dismiss();
            }
        });

        popupMenu.show();
    }

    private void fetch_latest_ref(){

        String url = "https://zaldivarservices.com/android_new/customer_app/get_latest_ref.php";

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String[] main_res = response.split(";");

                if (main_res[0].equals("N/R")){
                    generated_reference = main_res[1] + "0";
                } else {

                    String[] separate = response.split("<br>");

                    date = separate[0];
                    reference = separate[1];

                    int ref = Integer.parseInt(reference);

                    String[] filtered = reference.split("", 7);

                    StringJoiner joiner = new StringJoiner("");


                    for (String s : filtered) {
                        joiner.add(s);
                    }

                    String date_from_reference = joiner.toString();

                    if (date_from_reference.equals(date)){
                        generated_reference = String.valueOf(ref + 1);
                    } else {
                        generated_reference = date + "0";
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(sr);

    }



    private void send_now(){


        String url = "https://zaldivarservices.com/android_new/customer_app/send_order.php";

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Success")){

                    confirmation.dismiss();
                    Toast.makeText(MainActivity.this, "Order sent successfully!", Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed to send order. Please try again later!", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", usernameString);
                params.put("quantity", amount.getText().toString());
                params.put("reference", generated_reference);

                return params;
            }
        };
        queue.add(sr);

    }

    private void fetch_data(){

        String url = "https://zaldivarservices.com/android_new/customer_app/information.php";

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
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
                params.put("user", usernameString);
                return params;
            }
        };
        queue.add(sr);

    }

}