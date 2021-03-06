package com.androidbelieve.drawerwithswipetabs;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import static android.content.Context.MODE_PRIVATE;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.BOUNDS_INDIA;
import static com.androidbelieve.drawerwithswipetabs.MainActivity.LOG_TAG;


/**
 * Created by Ratan on 7/29/2015.
 */
public class LiftFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    AutoCompleteTextView saddr,daddr;
    RadioGroup genp;
    EditText seatno;
    Button picksub;
    String Genderpre,addrD,addrS;
    static final int GOOGLE_API_CLIENT_ID = 1;
    static GoogleApiClient mGoogleApiClient;
    static PlaceArrayAdapter mPlaceArrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lift_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setlolbar("CarPool");
        saddr=(AutoCompleteTextView)view.findViewById(R.id.saddret);
        daddr=(AutoCompleteTextView)view.findViewById(R.id.daddret);
        genp=(RadioGroup)view.findViewById(R.id.radioGroup);
        seatno=(EditText)view.findViewById(R.id.seatset);
        picksub=(Button)view.findViewById(R.id.pickupsubmit);
        Genderpre="male";
        addrS ="null";
        addrD = "null";
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
        picksub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AGlobal.gen=Genderpre;
                final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
                AGlobal.nos= Integer.parseInt(seatno.getText().toString());
                AGlobal.sa=addrS;
                AGlobal.da=addrD;
                AGlobal.em = prefs.getString("email",null);
                AGlobal.ui= prefs.getString("user_img",null);
                AGlobal.na=prefs.getString("name",null);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.containerView, new LiftSearchFragment()).commit();
            }
        });
        /////////////--------gender preference////////////
        ////////////places API///////////////////


        try{
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .build();}
        catch (Exception e){}

        saddr.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                BOUNDS_INDIA, null);
        saddr.setAdapter(mPlaceArrayAdapter);
        daddr.setOnItemClickListener(mAutocompleteClickListener2);
        daddr.setAdapter(mPlaceArrayAdapter);


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
    @Override
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
