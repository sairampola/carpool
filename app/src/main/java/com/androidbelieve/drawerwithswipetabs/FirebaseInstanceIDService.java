package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pain on 23/3/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        final String token= FirebaseInstanceId.getInstance().getToken();
        final SharedPreferences prefs = getSharedPreferences("carpool", MODE_PRIVATE);
        final String emai = prefs.getString("email",null);
        final String url2 = AGlobal.url;
        final Context con = getApplicationContext();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2+"registertoken",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(con,"Successfully requested!!",Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling

                //System.out.println("Something went wrong!");
                error.printStackTrace();

            }

        }){
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<String, String>();
                //params.put("name", "Androidhive");

                params.put("email", emai);
                params.put("token", token);

                return params;
            }

        };

// Add the request to the queue
        Volley.newRequestQueue(con).add(stringRequest);

    }
}
