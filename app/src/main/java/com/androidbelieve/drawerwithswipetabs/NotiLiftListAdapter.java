package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pain on 6/4/17.
 */

public class NotiLiftListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private Context con;
    private FragmentManager fml;
    private String emll;
    private List<NotiPickupResults> movieItems;
    NotiPickupResults m;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public NotiLiftListAdapter(Activity activity, List<NotiPickupResults> movieItems,Context con,FragmentManager fm) {
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
            convertView = inflater.inflate(R.layout.noti_lift_row_layout, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
       // NetworkImageView thumbNail = (NetworkImageView) convertView
                //.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.name2);
        //final Button acc = (Button)convertView.findViewById(R.id.Accept);
        //final Button rej = (Button)convertView.findViewById(R.id.Reject);
        m = movieItems.get(position);
        final String url2 = AGlobal.url;
        final SharedPreferences prefs = con.getSharedPreferences("carpool", MODE_PRIVATE);


/*
        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/////////////////////////////////////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2+"notiacceptreject",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Result handling
                                //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                try {
                                    Toast.makeText(con,"Rejected!!",Toast.LENGTH_SHORT).show();

                                    rej.setVisibility(View.GONE);
                                    acc.setVisibility(View.VISIBLE);

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

                        params.put("Aname", m.getTitle());
                        params.put("Aemail", m.getAemailid());
                        params.put("Bemail", m.getBemailid());
                        params.put("Status", "1");
                        return params;
                    }

                };

// Add the request to the queue
                Volley.newRequestQueue(con).add(stringRequest);







                //////////////////////////////////////////
/////////////////////////////////////////////////////////////



            }
        });


        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/////////////////////////////////////////////////////////////
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2+"notiacceptreject",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                // Result handling
                                //Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                try {
                                    Toast.makeText(con,"Accepted!!",Toast.LENGTH_SHORT).show();

                                    acc.setVisibility(View.GONE);
                                    rej.setVisibility(View.VISIBLE);

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

                        params.put("Aname", m.getTitle());
                        params.put("Aemail", m.getAemailid());
                        params.put("Bemail", m.getBemailid());
                        params.put("Status", "0");
                        return params;
                    }

                };

// Add the request to the queue
                Volley.newRequestQueue(con).add(stringRequest);







                //////////////////////////////////////////
/////////////////////////////////////////////////////////////



            }
        });
*/
        //TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row

        emll = m.getAemailid();

        // thumbnail image
    //    thumbNail.setImageUrl(AGlobal.url+m.getThumbnailUrl(), imageLoader);

        // title
        String d="";
        if(Integer.parseInt(m.getStatus())==0)
            d="accepted";
        else
            d = "rejected";
        title.setText(m.getBemailid() +" has "+d+" your request");

        // rating
        //rating.setText("Source Address: " + String.valueOf(m.getRating()));

        // genre
        //String genreStr = "lol";
        /*for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }


        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;*/
        final String finalD = d;
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AGlobal.em = m.getBemailid();

                //Toast.makeText(con,emll, Toast.LENGTH_SHORT).show();
                //FragmentManager fm = fml;
                ///FragmentTransaction ft=fm.beginTransaction();
                if(finalD.equals("accepted")) {
                    AGlobal.chdp=1;
                    FragmentTransaction ft = ((FragmentActivity) con).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.containerView, new ProfileFragment()).addToBackStack(null).commit();
                }
                else{
                    FragmentTransaction ft = ((FragmentActivity) con).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.containerView, new LiftProfileViewFragment()).addToBackStack(null).commit();
                }
            }
        });

        // release year
        //year.setText(String.valueOf(m.getYear()));

        return convertView;
    }
}
