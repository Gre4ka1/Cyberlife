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
        bots.add(new Bot(500,500,new short[]{20,17,18,20},new Color()));
        bots.add(bots.get(0).dublicate());
        System.out.println(bots.get(0).getCode());
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            for (Bot b:bots) {
                for (int i = 0; i < b.getCode().length; i++) {
                    switch (b.getCode()[i]){
                        case(17): b.generate(); break;
                        case(18): b.move(canvas); break;
                        //case(19): bots.add(b.dublicate());
                        case(20):
                            /*System.out.println("=====================");
                            System.out.println(b.getX()+" "+b.getY());
                            System.out.println(b.getDx()+" "+b.getDy());
                            System.out.println(b.getCurrent()[0]+" "+b.getCurrent()[1]);
                            System.out.println("=====================");*/
                            for (int h = 0; i<bots.size(); h++) {
                                if (bots.get(h).getX()==b.getCurrent()[0] || bots.get(h).getY()==b.getCurrent()[1]){
                                    b.eat(bots.get(h),bots);
                                    break;
                                }
                            }

                            break;
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
