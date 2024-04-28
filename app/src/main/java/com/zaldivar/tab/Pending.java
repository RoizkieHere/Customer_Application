package com.zaldivar.tab;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Pending extends Fragment {

    private Context context;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pending, container, false);
        context = getContext();
        fetch_data();
        return rootView;
    }


    private void fetch_data() {
        LinearLayoutCompat container = rootView.findViewById(R.id.pending_container);

        String url = "https://zaldivarservices.com/android_new/customer_app/pending_api/pending.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        SharedPreferences get_username = requireContext().getSharedPreferences("this_preferences", Context.MODE_PRIVATE);
        String user = get_username.getString("user", "");


        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (!response.isEmpty()){

                    container.setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.no_pending).setVisibility(View.GONE);

                    String[] row = response.split("<br>");

                    for (String column : row) {
                        String[] data = column.split(";");

                        View newView = LayoutInflater.from(context).inflate(R.layout.info_pending, container, false);

                        TextView reference_number, order_date, quantity, cancel_order;
                        reference_number = newView.findViewById(R.id.reference);
                        order_date = newView.findViewById(R.id.date);
                        quantity = newView.findViewById(R.id.quantity);
                        cancel_order = newView.findViewById(R.id.cancel_it);

                        reference_number.setText(data[0]);
                        order_date.setText(data[1]);

                        String[] quantity_split = data[2].split("\\.");
                        DecimalFormat df = new DecimalFormat("#,###");
                        DecimalFormat df_1 = new DecimalFormat("#,###.00");

                        if(quantity_split[1].equals("00")){
                            if (Integer.parseInt(quantity_split[0]) > 1){
                                quantity.setText(df.format(quantity_split[0]).concat(" Tons"));
                            } else {
                                quantity.setText(df.format(quantity_split[0]).concat(" Ton"));
                            }
                        } else {
                            if (Integer.parseInt(quantity_split[1]) > 0){
                                quantity.setText(df_1.format(quantity_split[0]).concat(" Tons"));
                            } else {
                                quantity.setText(df_1.format(quantity_split[0]).concat(" Ton"));
                            }
                        }

                        cancel_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pop_it(data[0]);
                            }
                        });

                        container.addView(newView);
                    }
                } else {

                    container.setVisibility(View.GONE);
                    rootView.findViewById(R.id.no_pending).setVisibility(View.VISIBLE);


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
                params.put("username", user);
                return params;
            }
        };
        queue.add(sr);
    }

    private void pop_it(String reference){

        Dialog confirmation =  new Dialog(context);

        confirmation.setContentView(R.layout.confirm_order_dialog);
        confirmation.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmation.setCancelable(false);
        confirmation.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button yes = confirmation.findViewById(R.id.yes);
        Button no = confirmation.findViewById(R.id.no);
        TextView question =  confirmation.findViewById(R.id.question);

        question.setText("Are you sure you want to cancel this item?");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel_it(reference, confirmation);

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

    private void cancel_it(String reference, Dialog confirmation){

        String url = "https://zaldivarservices.com/android_new/customer_app/pending_api/update_it.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Changed")){
                    confirmation.dismiss();
                    fetch_data();
                    Toast.makeText(context, "Item has been successfully cancelled.", Toast.LENGTH_SHORT).show();
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
                params.put("reference", reference);
                return params;
            }
        };
        queue.add(sr);







    }

    @Override
    public void onStop() {
        super.onStop();
        // Release resources here if needed
    }
}
