package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ratan on 7/29/2015.
 */
public class LiftSearchFragment extends Fragment {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private static final String TAG = "Cap";

    // Movies json url
    private static final String url = AGlobal.url+"lift";
    private ProgressDialog pDialog;
    private final List<LiftResult> movieList = new ArrayList<LiftResult>();
    private ListView listView;
    private  CustomListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.liftsearch_layout, null);


}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SharedPreferences prefs = getContext().getSharedPreferences("carpool", MODE_PRIVATE);



            listView = (ListView) view.findViewById(R.id.list);
            adapter = new CustomListAdapter(getActivity(), movieList,getContext(),getFragmentManager());
            listView.setAdapter(adapter);

            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();

            // changing action bar color
            //getActivity().getActionBar().setBackgroundDrawable(
                    //new ColorDrawable(Color.parseColor("#1b1b1b")));

            // Creating volley request obj
            StringRequest movieReq = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String respons) {
                            Log.d(TAG, respons);
                            hidePDialog();
                            JSONArray response = null;
                            try {
                                response = new JSONArray(respons);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    LiftResult movie = new LiftResult();
                                    movie.setTitle(obj.getString("name"));
                                    movie.setThumbnailUrl(obj.getString("user_img"));
                                    movie.setRating(obj.getString("source_address"));
                                    movie.setGenre(obj.getString("destination_address"));
                                    movie.setEmailid(obj.getString("email"));
                                    //movie.setYear(obj.getInt("seats"));

                                    // Genre is json array
                                    /*JSONArray genreArry = obj.getJSONArray("genre");
                                    ArrayList<String> genre = new ArrayList<String>();
                                    for (int j = 0; j < genreArry.length(); j++) {
                                        genre.add((String) genreArry.get(j));
                                    }
                                    movie.setGenre(genre);*/

                                    // adding movie to movies array
                                    movieList.add(movie);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<String, String>();
                    //params.put("name", "Androidhive");

                    params.put("gender", AGlobal.gen);
                    params.put("seats", String.valueOf(AGlobal.nos));
                    params.put("name", AGlobal.na);
                    params.put("user_img", AGlobal.ui);
                    params.put("email", prefs.getString("email",null));
                    params.put("source_address", AGlobal.sa);
                    params.put("destination_address", AGlobal.da);
                    Long lo = System.currentTimeMillis()/1000;
                    params.put("timestamp",lo.toString());


                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(movieReq);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            hidePDialog();
        }

        private void hidePDialog() {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }


    }

