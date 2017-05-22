package com.example.pc.scansdanimdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by PC on 04/19/2017.
 */

public class ScanView extends View {


    int height = 0;
    boolean up = false;

    Bitmap background = BitmapFactory.decodeResource(getResources(),
            R.drawable.blue_bg);
    Bitmap inside = BitmapFactory.decodeResource(getResources(),
            R.drawable.sd_card_inside_new);
    Bitmap round = BitmapFactory.decodeResource(getResources(),
            R.drawable.ic_round_bg_big);
    Bitmap dot = BitmapFactory.decodeResource(getResources(),
            R.drawable.ic_scan_speed);
    Bitmap outside = BitmapFactory.decodeResource(getResources(),
            R.drawable.sd_outside);
    Path path = new Path();


    Bitmap resize;

    int cx = (int)SizeScreen.width/2;
    int cy = (int)SizeScreen.height/2;
    int c = 400;

    boolean lenleft = false;
    boolean lenright  =false;

    RoundRunning roundRunningleft = new RoundRunning();
    RoundRunning roundRunningtright = new RoundRunning();

    double left = 180*Math.PI/180;
    double right = left;

    Handler handler = new Handler();

    Runnable runnable =  new Runnable() {
        @Override
        public void run() {

            invalidate();
            Log.e("run","fafsafafsafa");
            handler.postDelayed(this,35);

        }
    };

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        resizeImage();
        startAnim();
    }

    private void startAnim(){
        Log.e(ScanView.class.getSimpleName(),cx+" : "+cy);
        handler.post(runnable);
    }

    private void stopAnim(){
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e(ScanView.class.getSimpleName(),"onDraw");

        //outside = Bitmap.createScaledBitmap(outside,300,400,false);

        //resizeImage();

        canvas.drawBitmap(inside,cx-inside.getWidth()/2,cy-inside.getHeight()/2,null);
        canvas.drawBitmap(round,cx-round.getHeight()/2,cy-round.getHeight()/2,null);

        //canvas.drawBitmap(dot,cx-inside.getWidth()/2+inside.getWidth()/2,cy-inside.getHeight()+inside.getHeight()/2,null);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);
        p.setStrokeWidth(10);
        canvas.drawPoint(cx,cy,p);

        if (!lenleft){
            left+=0.1;
            //len = true;
        }
        else{
            left-=0.1;
        }

        if (left>=Math.PI*2){
            lenleft = true;
        }
        if (left<=Math.PI)
        {
            lenleft = false;
        }


        if (!lenright){
            right-=0.1;
        }
        else{
            right+=0.1;
        }

        if (right<=0){
            lenright = true;
        }
        if (right>=Math.PI) {
            lenright = false;
        }
        double ahihi = left - right;
        ahihi = ahihi*180/Math.PI;

        Log.e("gafsafsa",ahihi+"----->");

        roundRunningleft.setX(cx+Math.sin(left)*c);
        roundRunningleft.setY(cy+Math.cos(left)*c);

        canvas.drawBitmap(dot,(float)roundRunningleft.getX()-dot.getWidth()/2,(float) roundRunningleft.getY()-dot.getHeight()/2,null);

        //start+=0.1;
        Log.e(ScanView.class.getSimpleName(),"start "+left);

        height = (int)calcHeightOfOutSideImage(cy-inside.getHeight()/2,up);

        if (up) {
            up = true;
            if (height<=0){
                up = false;
            }
        }
        else if (height<outside.getWidth() && up == false) {
            if (height>=outside.getHeight()) {
                up = true;
            }
        }


        if (height>inside.getHeight())
            height = inside.getHeight();

        Log.e(ScanView.class.getSimpleName(),"chieu cao:"+height);

        roundRunningtright.setX(cx+Math.sin(right)*c);
        roundRunningtright.setY(cy+Math.cos(right)*c);
        canvas.drawBitmap(dot,(float)roundRunningtright.getX()-dot.getWidth()/2,(float) roundRunningtright.getY()-dot.getHeight()/2,null);

        final RectF oval = new RectF();
        oval.set(cx-c,cy-c,cx+c,cy+c);
        Paint drawline = new Paint();
        drawline.setAntiAlias(true);
        drawline.setStyle(Paint.Style.STROKE);
        drawline.setStrokeWidth(10);
        drawline.setColor(Color.WHITE);
        double alpha = 270 - ahihi/2;
        canvas.drawArc(oval,(float) alpha,(float)ahihi,false,drawline);


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);

        canvas.drawLine((float)roundRunningleft.getX(),(float)roundRunningleft.getY(),(float)roundRunningtright.getX(),(float)roundRunningtright.getY(),paint);


        Log.e(ScanView.class.getSimpleName(),height+"");
        resize = Bitmap.createBitmap(outside,0,0,300,height);
        canvas.drawBitmap(resize,cx-inside.getWidth()/2,cy-inside.getHeight()/2,null);



        //startAnim();

    }

    private void resizeImage(){
        inside = Bitmap.createScaledBitmap(inside,300,400,false);
        outside = Bitmap.createScaledBitmap(outside,300,400,false);
        round = Bitmap.createScaledBitmap(round,c*2,c*2,false);
    }

    private double calcHeightOfOutSideImage(int y, boolean isUp){
        double heightofImage = 1;
       if (!isUp){
           heightofImage = roundRunningleft.getY()-y;
       }
        else
           heightofImage = y - roundRunningleft.getY();
        if (heightofImage>0)
            return  heightofImage;
        return 1;
    }



}
