package com.example.frodog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapView;

//미완성
public class Map extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapView mapView=new MapView(this);
       RelativeLayout mapviewcontainer = (RelativeLayout) findViewById(R.id.map_view);
       mapviewcontainer.addView(mapView);
    }
}
