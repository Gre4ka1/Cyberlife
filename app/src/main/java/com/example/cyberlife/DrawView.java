package com.example.cyberlife;


import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    public int cx,cy;
    public volatile Context context;
    public void getContext1(){

    }

    private DrawThread drawThread;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public boolean onTouchEvent(MotionEvent event) {
        cx=(int) event.getX();
        cy=(int) event.getY();
        return super.onTouchEvent(event);
        //return true;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }
}
