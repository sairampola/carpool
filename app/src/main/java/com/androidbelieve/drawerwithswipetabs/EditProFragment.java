package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.androidbelieve.drawerwithswipetabs.AGlobal.url;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.BOUNDS_INDIA;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.LOG_TAG;

/**
 * Created by Ratan on 7/29/2015.
 */
public class EditProFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    EditText name,phone,password;
    TextView email;
    AutoCompleteTextView h_addr,c_addr;
    RadioGroup gen;
    RadioButton m,f;
    Button lic,editsub;
    String uploadL,addrH,addrC,Gender,email00,lice;
    int fg;

    static final int GOOGLE_API_CLIENT_ID = 4;
    static GoogleApiClient mGoogleApiClient;
    static PlaceArrayAdapter mPlaceArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editpro_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=(EditText)view.findViewById(R.id.nameedit);
        phone=(EditText)view.findViewById(R.id.phoneedit);
        password=(EditText)view.findViewById(R.id.pwedit);
        email=(TextView)view.findViewById(R.id.emailnoedit);
        h_addr=(AutoCompleteTextView) view.findViewById(R.id.haddredit);
        c_addr=(AutoCompleteTextView) view.findViewById(R.id.caddredit);
        password=(EditText)view.findViewById(R.id.pwedit);
        gen=(RadioGroup)view.findViewById(R.id.editradiogrp);
        lic=(Button)view.findViewById(R.id.licedit);
        editsub=(Button)view.findViewById(R.id.editsub);
        m=(RadioButton)view.findViewById(R.id.male);
        f=(RadioButton)view.findViewById(R.id.female);
        uploadL="null";
        addrC ="null";
        addrH = "null";
        Gender = "null";
        email00="null";
        lice="null";
        fg=0;


        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
        final String emaill = prefs.getString("email","lol");

        final String url = AGlobal.url;


        /////////////////////////////////////////////
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"profile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        try {
                            final JSONObject jo= new JSONObject(response);
                            name.setText(jo.getString("name"));
                            email.setText(jo.getString("email"));
                            email00=jo.getString("email");
                            phone.setText(jo.getString("phone"));
                            password.setText(jo.getString("password"));
                            //String gender="null";
                            Gender=jo.getString("gender");
                            h_addr.setText(jo.getString("home_address"));
                            addrH=jo.getString("home_address");
                            c_addr.setText(jo.getString("company_address"));
                            addrC=jo.getString("company_address");
                            uploadL=jo.getString("license");


                            if(Gender.equals("male"))
                            {
                                m.setChecked(true);
                            }
                            else
                                f.setChecked(true);

                           // Glide.with(getActivity()).load(url+jo.getString("user_img")).fitCenter().into(ui);
                            //Glide.with(getActivity()).load(url+jo.getString("license")).fitCenter().into(lic);
                            /*ui.setOnClickListener(new View.OnClickListener()
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
                            });*/
                            /*lic.setOnClickListener(new View.OnClickListener()
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
                            });*/



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

                params.put("email", emaill);

                return params;
            }

        };

// Add the request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);

        /////////////////////////////////////////////






///////////////////////////////////////////////////////
        ////////////radiogrp//////////
        gen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.male) {
                    Gender="male";

                } else {
                    Gender="female";
                }
            }
        });
        //////////------radiogrp//////
        ///////////////places api////////////////


        try{
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .build();}
        catch (Exception e){}

        h_addr.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                BOUNDS_INDIA, null);
        h_addr.setAdapter(mPlaceArrayAdapter);
        c_addr.setOnItemClickListener(mAutocompleteClickListener2);
        c_addr.setAdapter(mPlaceArrayAdapter);
        ///////////////----places api//////////////
        //////////////upload image/////////////////

        lic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        /////////////-------upload image//////////



        /////register ///////////////
        editsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Registering .....", Toast.LENGTH_SHORT).show();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams rp = new RequestParams();
                rp.put("name", name.getText().toString());
                rp.put("phone", phone.getText().toString());
                rp.put("password", password.getText().toString());
                rp.put("gender", Gender);
                rp.put("company_address", addrC);
                rp.put("home_address", addrH);
                rp.put("email", email00);
                if(fg==1)
                {
                try {
                    rp.put("license", new File(uploadL));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }}
                else
                    rp.put("license", uploadL);

                /*if (uploadL.equals("null"))
                    rp.put("license", "null");
                else
                    try {
                        rp.put("license", new File(uploadL));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/


                client.post(AGlobal.url + "editprofile", rp, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String error = null;
                        try {
                            JSONObject loljson = new JSONObject(new String(responseBody, "UTF-8"));
                            error = loljson.getString("error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), "Edited Successfully", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.containerView, new ProfileFragment()).commit();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getContext(), "Please Try Again ", Toast.LENGTH_SHORT).show();
                    }
                });

                ///////////////////////////////////////////////


            }


        });

    }
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 143);
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            // Get the url from data
            Uri selectedImageUri = data.getData();

            if (null != selectedImageUri) {


                String wholeID = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    wholeID = DocumentsContract.getDocumentId(selectedImageUri);
                }

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                String[] column = {MediaStore.Images.Media.DATA};

                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);


                String filePath = "";

                int columnIndex = cursor.getColumnIndex(column[0]);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
                String path = getRealPathFromURI(getContext(), selectedImageUri);

                if (requestCode == 143) {
                    uploadL=filePath;
                    fg=1;
                    Log.i("image", "LLLLImage Path : " + filePath);
                }
            }}}
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            addrH = (String) item.description;
            Log.i("place",addrH);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };
    private AdapterView.OnItemClickListener mAutocompleteClickListener2
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            addrC = (String) item.description;
            Log.i("place",addrC);

            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getContext(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }



}