package com.lk.finalcodefestapp1;


import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

public class Directionpage extends AppCompatActivity {

    public String timetxt;
    public String distancetxt;

    public String getNewsID() {
        return NewsID;
    }

    public void setNewsID(String newsID) {
        NewsID = newsID;
    }

    private String NewsID;

    TextView distance,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directionpage);


        Bundle bundle = getIntent().getExtras();

        NewsID = bundle.getString("NewsID");

        distance=findViewById(R.id.distance);
        time=findViewById(R.id.expecttime);










    }


    public void setDuration(String timetext){

        this.timetxt=timetext;
        time.setText(timetxt+"");

    }

    public void setEstimatedvalue(String distancetext) {

        this.distancetxt=distancetext;
        distance.setText(distancetxt+"");

    }
}