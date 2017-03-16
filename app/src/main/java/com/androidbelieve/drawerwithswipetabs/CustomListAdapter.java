package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by pain on 17/2/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private Context con;
    private FragmentManager fml;
    private String emll;
    private List<LiftResult> movieItems;
    LiftResult m;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<LiftResult> movieItems,Context con,FragmentManager fm) {
        this.activity = activity;
        this.movieItems = movieItems;
        this.con = con;
        this.fml=fm;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.name2);
        TextView rating = (TextView) convertView.findViewById(R.id.saddr);
        TextView genre = (TextView) convertView.findViewById(R.id.daddr);
        Button req = (Button)convertView.findViewById(R.id.reqlist);
         m = movieItems.get(position);
        final String url2 = AGlobal.url;
        final SharedPreferences prefs = con.getSharedPreferences("carpool", MODE_PRIVATE);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/////////////////////////////////////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2+"notipickuprequest",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Result handling
                                //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                try {
                                    Toast.makeText(con,"Successfully requested!!",Toast.LENGTH_SHORT).show();


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

                        params.put("Aname", prefs.getString("name","lol"));
                        params.put("Aemail", prefs.getString("email","lol"));
                        params.put("Bemail", m.getEmailid());
                        params.put("Apropic", prefs.getString("user_img","lol"));

                        return params;
                    }

                };

// Add the request to the queue
                Volley.newRequestQueue(con).add(stringRequest);







                //////////////////////////////////////////
/////////////////////////////////////////////////////////////



            }
        });


        //TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row

        emll = m.getEmailid();

        // thumbnail image
        thumbNail.setImageUrl(AGlobal.url+m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        //rating.setText("Source Address: " + String.valueOf(m.getRating()));

        // genre
        //String genreStr = "lol";
        /*for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }


        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;*/
        rating.setText("Source Address: " + String.valueOf(m.getRating()));
        genre.setText("Destination Address: " + String.valueOf(m.getGenre()));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AGlobal.em = emll;
                Toast.makeText(con,emll,Toast.LENGTH_SHORT).show();
                FragmentManager fm = fml;
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.containerView,new LiftProfileViewFragment()).commit();
            }
        });

        // release year
        //year.setText(String.valueOf(m.getYear()));

        return convertView;
    }


}
