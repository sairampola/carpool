package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

/**
 * Created by Ratan on 7/29/2015.
 */
public class ProfileFragment extends Fragment {

    ImageView ui,li;
    Button ep,ci;
    TextView nm,em,ph,gen,ha,ca;
    String uploadP,emaill;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setlolbar("Profile");
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
        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);
        emaill = prefs.getString("email","lol");

        final String url = AGlobal.url;
        uploadP="null";

        ///////////edit profile button/////
        ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
                //toolbar.setTitle("Register");
                ((MainActivity)getActivity()).setlolbar("Edit Profile");
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.containerView,new EditProFragment()).commit();

            }
        });
        //////////----edit profile button//////

     ////////////change img/////////////
        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });





        ///////////--------change img////////////


















        //////////////////////////////////////////
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
                            ph.setText("Phone: "+jo.getString("phone"));

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

// Add the request to the queue
        Volley.newRequestQueue(getContext()).add(stringRequest);





        //////////////////////////////////////////
    }
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 341);
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

                if (requestCode == 341) {
                    uploadP=filePath;
                    Toast.makeText(getContext(),"Updating .....", Toast.LENGTH_SHORT).show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams rp = new RequestParams();
                    try {
                        rp.put("user_img", new File(uploadP));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    rp.put("email",emaill);

                    client.post(AGlobal.url + "changedp", rp, new AsyncHttpResponseHandler() {
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
                            Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
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



                    Log.i("image", "PPPPImage Path : " + filePath);
                }



            }}}


}
