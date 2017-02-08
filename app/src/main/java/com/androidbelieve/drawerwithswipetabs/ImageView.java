package com.androidbelieve.drawerwithswipetabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import ooo.oxo.library.widget.TouchImageView;

public class ImageView extends AppCompatActivity {

    TouchImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        iv = (TouchImageView) findViewById(R.id.touch_image);
        String url = getIntent().getStringExtra("imageurl");
        Glide.with(this).load(url).fitCenter().into(iv);
    }
}
