package com.example.cyberlife;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class DrawThread extends Thread{
    private final SurfaceHolder surfaceHolder;

    private volatile boolean running = true;//флаг для остановки потока

    Paint background = new Paint();
    Paint paint = new Paint();


    {
        background.setColor(Color.WHITE);
        background.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        ArrayList<Bot> bots=new ArrayList<>();
        bots.add(new Bot(500,500,new short[]{17,18},new Color()));
        bots.add(bots.get(0).dublicate());
        System.out.println(bots.get(0).getCode());
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            for (Bot b:bots) {
                for (int i = 0; i < b.getCode().length; i++) {
                    switch (b.getCode()[i]){
                        case(17): b.generate(); break;
                        case(18): b.move(); break;
                        //case(19): bots.add(b.dublicate());
                        default:
                            System.out.println("BRUH "+b.getCode()[i]);

                    }
                }
            }

//========================================отрисовка===========================================
            if (canvas != null) {
                try {
                    canvas.drawRect(0,0, canvas.getWidth(),canvas.getHeight(),background);
                    for (Bot i:bots) {
                        i.draw(canvas,paint);
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
//========================================отрисовка===========================================
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("BLYAT!");
            }
        }
    }
}
