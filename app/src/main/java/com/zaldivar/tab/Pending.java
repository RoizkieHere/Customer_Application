package com.zaldivar.tab;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Pending extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_pending, container, false);

        String url = "https://zaldivarservices.com/customer_app/pending.php";
        RequestQueue requestQueue = Volley.newRequestQueue(rootview.getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LinearLayoutCompat container1 = (LinearLayoutCompat) rootview.findViewById(R.id.pending_container);

                        LayoutInflater inflater = LayoutInflater.from(getContext());

                        String[] row = response.split("<br>");

                        for (String data : row) {

                            View newview = inflater.inflate(R.layout.pending_table_row, container1, false);

                            TextView date = newview.findViewById(R.id.date);
                            TextView weight = newview.findViewById(R.id.weight);

                            String[] columns = data.split(";");

                            date.setText(columns[0]);
                            weight.setText(columns[1]);



                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "maliii", Toast.LENGTH_SHORT).show();
                        Log.d("demo", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "Abrera.123");
                return params;
            }
        };

        requestQueue.add(stringRequest);






        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);






    }







}

















