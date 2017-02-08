package com.androidbelieve.drawerwithswipetabs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.BOUNDS_INDIA;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.LOG_TAG;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PickupFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    AutoCompleteTextView saddr,daddr;
    RadioGroup genp;
    EditText seatno;
    Button picksub;
    String Genderpre,addrD,addrS;
    static final int GOOGLE_API_CLIENT_ID = 0;
    static GoogleApiClient mGoogleApiClient;
    static PlaceArrayAdapter mPlaceArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pickup_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saddr=(AutoCompleteTextView)view.findViewById(R.id.saddret);
        daddr=(AutoCompleteTextView)view.findViewById(R.id.daddret);
        genp=(RadioGroup)view.findViewById(R.id.radioGroup);
        seatno=(EditText)view.findViewById(R.id.seatset);
        picksub=(Button)view.findViewById(R.id.pickupsubmit);
        Genderpre="male";
        addrS ="null";
        addrD = "null";
        /////////gender preference///////////
        genp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.male) {
                    Genderpre="male";

                } else if(checkedId==R.id.female)
                {
                    Genderpre="female";

                }
                else
                     Genderpre="none";
            }
        });
        /////////////--------gender preference////////////
        ////////////places API///////////////////


//        mGoogleApiClient.disconnect();
        try{
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();}
        catch (Exception e){

        }

        saddr.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                BOUNDS_INDIA, null);
        saddr.setAdapter(mPlaceArrayAdapter);
        daddr.setOnItemClickListener(mAutocompleteClickListener2);
        daddr.setAdapter(mPlaceArrayAdapter);





        ////////////----------------places Api///////////
        final String url = AGlobal.url;
        picksub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url+"pickup",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Result handling
                                //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                try {
                                    final JSONObject jo= new JSONObject(response);

                                    System.out.println("aaaaaaa went wrong!");



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
                        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
                        final String emaill = prefs.getString("email","lol");
                        final String namee=prefs.getString("name","lol");
                        final String userimg=prefs.getString("user_img","lol");


                        params.put("email",emaill );
                        params.put("name",namee );
                        params.put("user_img",userimg );
                        params.put("source_address",addrS );
                        params.put("destination_address",addrD );
                        Long lo = System.currentTimeMillis()/1000;
                        params.put("timestamp",lo.toString());
                        params.put("gender",Genderpre );
                        params.put("seats",seatno.getText().toString() );


                        return params;
                    }

                };

// Add the request to the queue
                Volley.newRequestQueue(getContext()).add(stringRequest);




                //////////////////////////////////////////
            }
        });

    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            addrS = (String) item.description;
            Log.i("place",addrS);
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
            addrD = (String) item.description;
            Log.i("place",addrD);

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
