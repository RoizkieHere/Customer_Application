package com.zaldivar.tab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    String email_address, usernameString;

    ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("this_preferences", MODE_PRIVATE);

        if(!sharedPreferences.contains("username")){
            Intent supplier = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(supplier);
        }


        Intent intent = getIntent();
        email_address = intent.getStringExtra("email");
        usernameString = intent.getStringExtra("user");


        tabLayout = findViewById(R.id.tablayout);
        viewPager =  findViewById(R.id.viewpager);
        settings = findViewById(R.id.settings);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new Pending(), "Pending");
        vpAdapter.addFragment(new Transit(), "On Delivery");
        vpAdapter.addFragment(new Completed(), "Completed");
        vpAdapter.addFragment(new Completed(), "Cancelled");
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

                    Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent);

                    return true;
                } else if (item.getItemId() == R.id.change_password){

                    Intent to_forgot = new Intent(MainActivity.this, Change.class);
                    startActivity(to_forgot);

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

}