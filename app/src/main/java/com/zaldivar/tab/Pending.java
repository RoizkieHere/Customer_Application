package com.zaldivar.tab;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;


public class Pending extends Fragment {

    Context context;
    View rootview;
    String url = "https://zaldivarservices.com/customer_app/pending.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fetch_data();

        context = getContext();
        // Inflate the layout for this fragment
        rootview =  inflater.inflate(R.layout.fragment_pending, container, false);
        return  rootview;

    }

    private void fetch_data(){

        LinearLayoutCompat container = rootview.findViewById(R.id.pending_container) ;
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String[] row = response.split("<br>");

                for (String column : row){

                    String[] data = column.split(";");

                    View new_view = inflater.inflate(R.layout.pending_table_row, container, false);

                    TextView weight = new_view.findViewById(R.id.weight);
                    TextView date = new_view.findViewById(R.id.date);
                    TextView unit = new_view.findViewById(R.id.unit);

                    weight.setText(data[0]);
                    date.setText(data[1]);
                    unit.setText("Ton");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user","Abrera.123");

                return params;
            }

        };
        queue.add(sr);
    }





}

















