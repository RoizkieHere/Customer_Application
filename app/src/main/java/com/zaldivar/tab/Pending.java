package com.zaldivar.tab;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class Pending extends Fragment {

    String url = "https://srv829-files.hstgr.io/42e27937cf48f8c6/files/public_html/customer%20app/pending.php";
    Context this_context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);

    }


    StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }){
        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();

            params.put("user","username");


            return params;
        }

    };

}

















