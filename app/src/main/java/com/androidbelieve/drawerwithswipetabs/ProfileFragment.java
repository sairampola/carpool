package com.androidbelieve.drawerwithswipetabs;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.juliomarcos.ImageViewPopUpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ratan on 7/29/2015.
 */
public class ProfileFragment extends Fragment {

    ImageView ui,li;
    Button ep,ci;
    TextView nm,em,ph,gen,ha,ca;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ui=(ImageView)view.findViewById(R.id.userimg);
        li=(ImageView)view.findViewById(R.id.licenseimg);
        ep=(Button)view.findViewById(R.id.editprofile);
        ci=(Button)view.findViewById(R.id.changeimg);
        nm=(TextView)view.findViewById(R.id.name);
        em=(TextView)view.findViewById(R.id.email);
        ph=(TextView)view.findViewById(R.id.phone);
        gen=(TextView)view.findViewById(R.id.gender);
        ha=(TextView)view.findViewById(R.id.homeadrr);
        ca=(TextView)view.findViewById(R.id.compaddr);

        final String url = AGlobal.url;


        //////////////////////////////////////////
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jo= new JSONObject(response);
                            nm.setText("Name: "+jo.getString("name"));
                            em.setText("Email: "+jo.getString("email"));
                            ph.setText("Phone: "+jo.getString("phone"));

                            gen.setText("Gender: "+jo.getString("gender"));
                            ha.setText("Home Address: "+jo.getString("home_address"));
                            ca.setText("Company Address: "+jo.getString("company_address"));
                            Glide.with(getActivity()).load(url+jo.getString("user_img")).placeholder(R.drawable.loading).fitCenter().into(ui);
                            Glide.with(getActivity()).load(url+jo.getString("license")).placeholder(R.drawable.loading).fitCenter().into(li);
                            ui.setOnClickListener(new View.OnClickListener()
                            {
                                @Override public void onClick(View v) {
                                    ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), ui);
                                }
                            });
                            li.setOnClickListener(new View.OnClickListener()
                            {
                                @Override public void onClick(View v) {
                                    ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), li);
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

                params.put("email", "spp@spp.com");

                //params.put("password", "password123");

                return params;
            }

        };

// Add the request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);











        //////////////////////////////////////////
    }
}
