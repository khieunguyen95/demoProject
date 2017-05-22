package com.example.pc.scansdanimdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayMetrics=getResources().getDisplayMetrics();
        getScreenSIze();
        setContentView(R.layout.activity_main);
        getScreenSIze();
       // ScanView scanView = new ScanView(this);
        //scanView.starA
    }

    public void getScreenSIze(){
        //DisplayMetrics displaymetrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;

        //int[] size={w,h};
        SizeScreen.height = h;
        SizeScreen.width = w;

    }
}
