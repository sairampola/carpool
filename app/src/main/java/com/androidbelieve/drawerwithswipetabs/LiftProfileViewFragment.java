package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ratan on 7/29/2015.
 */
public class LiftProfileViewFragment extends Fragment {

    android.widget.ImageView ui,li;
    TextView nm,em,gen,ha,ca;
    String uploadP,emaill;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lift_profile_view_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ui=(android.widget.ImageView)view.findViewById(R.id.userimg);
        li=(ImageView)view.findViewById(R.id.licenseimg);
        nm=(TextView)view.findViewById(R.id.name);
        em=(TextView)view.findViewById(R.id.email);
        gen=(TextView)view.findViewById(R.id.gender);
        ha=(TextView)view.findViewById(R.id.homeadrr);
        ca=(TextView)view.findViewById(R.id.compaddr);
        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
        emaill = prefs.getString("email","lol");

        final String url = AGlobal.url;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        try {
                            final JSONObject jo= new JSONObject(response);
                            nm.setText("Name: "+jo.getString("name"));
                            em.setText("Email: "+jo.getString("email"));
                            gen.setText("Gender: "+jo.getString("gender"));
                            ha.setText("Home Address: "+jo.getString("home_address"));
                            ca.setText("Company Address: "+jo.getString("company_address"));
                            Glide.with(getActivity()).load(url+jo.getString("user_img")).fitCenter().into(ui);
                            Glide.with(getActivity()).load(url+jo.getString("license")).fitCenter().into(li);
                            ui.setOnClickListener(new View.OnClickListener()
                            {
                                @Override public void onClick(View v) {
                                    //ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), ui);
                                    Intent i = new Intent(getContext(), com.androidbelieve.drawerwithswipetabs.ImageView.class);
                                    try {
                                        i.putExtra("imageurl",url+jo.getString("user_img"));
                                        startActivity(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            li.setOnClickListener(new View.OnClickListener()
                            {
                                @Override public void onClick(View v) {
                                    //ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), li);
                                    Intent i = new Intent(getContext(),com.androidbelieve.drawerwithswipetabs.ImageView.class);
                                    try {
                                        i.putExtra("imageurl",url+jo.getString("license"));
                                        startActivity(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });



                        } catch (JSONException e) {
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

                //params.put("email", emaill);

                params.put("email", AGlobal.em);
                return params;
            }

        };
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }







}
