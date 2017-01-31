package com.androidbelieve.drawerwithswipetabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ratan on 7/29/2015.
 */
public class LoginFragment extends Fragment {

    EditText em,pw;
    Button sub,sign;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout,null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        em=(EditText)view.findViewById(R.id.email);
        pw=(EditText)view.findViewById(R.id.password);
        sub=(Button)view.findViewById(R.id.submit);
        sign=(Button)view.findViewById(R.id.signup);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
                //toolbar.setTitle("Register");
                ((MainActivity)getActivity()).setlolbar("Register");
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.containerView,new RegisterFragment()).commit();

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emaillol,passwordlol;
                emaillol = em.getText().toString();
                passwordlol = pw.getText().toString();
                String url = "http://10.10.64.109:3000/login";
                ////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Result handling
                                //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject jo= new JSONObject(response);
                                    int status = jo.getInt("error");
                                    if(status ==0)
                                    {
                                        ((MainActivity)getActivity()).setlolbar("CarPool");
                                        FragmentManager fm=getFragmentManager();
                                        FragmentTransaction ft=fm.beginTransaction();
                                        ft.replace(R.id.containerView,new HomeFragment()).commit();
                                    }
                                    else
                                        Toast.makeText(getContext(),"Invalid email/password",Toast.LENGTH_LONG).show();
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

                        params.put("email", emaillol);
                        params.put("password",passwordlol);
                        //params.put("password", "password123");

                        return params;
                    }

                };

// Add the request to the queue
                Volley.newRequestQueue(getContext()).add(stringRequest);









                ////////////////////////////
            }
        });


    }
}
