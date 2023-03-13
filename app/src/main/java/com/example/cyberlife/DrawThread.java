package com.example.cyberlife;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class DrawThread extends Thread{
    private final SurfaceHolder surfaceHolder;
    private static boolean clickF=false;
    private static int cy,cx;
    private volatile boolean running = true;//флаг для остановки потока
    private Bot infoBot=null;
    Paint background = new Paint();
    Paint paint = new Paint();


    {
        background.setColor(Color.WHITE);
        background.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    public static void click(int x,int y){
        //System.out.println(x+" "+y);
        clickF=true;
        cx=x;
        cy=y;
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
        bots.add(new Bot(500,500,new short[]{24,18,1,34,12,2,9,6,3,6,4,6,9,6,12,12},new Color()));
        //===========================добавление ботов===================================

        for (int i = 0; i < 10; i++) {
            Canvas canvas = surfaceHolder.lockCanvas();
            try{
                int newx = (int)(new Random().nextInt(canvas.getWidth()) /50)*50;
                int newy = (int)(new Random().nextInt(canvas.getHeight())/50)*50;
                boolean f3=true;
                for (Bot j:bots) {
                    if(j.getX()==newx && j.getY()==newy){
                        f3=false;
                        break;
                    }
                }
                if (f3){
                    bots.add(new Bot(newx,newy));
                }
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }



        //==============================================================================
        //bots.add(new Bot(500,500,new short[]{24,20,19,34,12,2,9,6,3,6,4,6,9,6,12,12},new Color()));
        //bots.add(bots.get(0).dublicate());
        System.out.println(bots.get(0).getCode());
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            //System.out.println("cfygvuhbijnojljgkfhgdzjxfhcgvjhkb------");

//========================================отрисовка===========================================
            if (canvas != null) {
                //System.err.println("==============+++++++++");
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), background);
                    boolean f = false;
                    boolean f2 = false;
                    for (Bot i : bots) {
                        i.draw(canvas, paint);
                        if (clickF) {
                            clickF = false;
                            f2 = true;
                            if (cx > i.getX() && cx < i.getX() + 50 && cy > i.getY() && cy < i.getY() + 50) {
                                infoBot = i;
                                f = true;
                                //System.out.println(i);
                            }

                            //System.out.println(infoBot);

                            System.out.println(cx + " / " + cy);
                        }
                        if (i == infoBot) {
                            i.drawInfo(canvas);
                        }
                    }
                    if (!f && f2) infoBot = null;

                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
            //else System.out.println("==============");
//========================================отрисовка===========================================
            for (int bb = 0; bb < bots.size(); bb++) { //TODO cdelay blyat chtob pri udalenii ne lomalsya cikl
                Bot b = bots.get(bb);
                for (short count = 0; count < b.getCode().length; ) {
                    //switch (b.getCode()[count]){

                    if (b.getCode()[count] == 17) {//case(17): {   //                      ГЕНЕРАЦИЯ
                        b.generate();
                        count++;
                        //break;
                    }
                    else if (b.getCode()[count] == 18) {//case(18): {   //                       ДВИЖЕНИЕ
                        b.move(canvas);
                        count++;
                        //break;
                    }
                    else if (b.getCode()[count] == 19) {//case(19):     //                      РАЗМНОЖЕНИЕ
                        bots.add(b.dublicate());
                        //System.out.println(b.getDx() + " " + b.getDy());
                        //break;
                    }
                    else if (b.getCode()[count] == 20) {//case(20): {   //                      ПОЕДАНИЕ
                        System.out.println("=====================");
                        System.out.println(b.getX() + " " + b.getY());
                        System.out.println(b.getDx() + " " + b.getDy());
                        System.out.println(b.getCurrent()[0] + " " + b.getCurrent()[1]);
                        System.out.println("=====================");
                        for (int h = 0; h < bots.size(); h++) {
                            if (bots.get(h).getX() == b.getCurrent()[0] || bots.get(h).getY() == b.getCurrent()[1]) {
                                System.out.println("SUCCESS!");
                                b.eat(bots.get(h), bots);
                                //break;
                            }
                        }
                        count++;
                        //break;
                    }
                    else if (b.getCode()[count] == 21) {//case(21): {  //                       ПОВОРОТ ВЛЕВО
                        b.rotateL();
                        count++;
                        //break;
                    }
                    else if (b.getCode()[count] == 22) {//case(22): {   //                      ПОВОРОТ ВПРАВО
                        b.rotateR();
                        count++;
                        //break;
                    }
                    else if (b.getCode()[count] == 23) {//case(23): {   //                      ПРОВЕРКА ЭНЕРГИИ
                        count += b.energy();
                    }
                    else if (b.getCode()[count] == 24) {//case(24):    //                      ПРОВЕРКА ВПЕРЕДИСТОЯЩЕГО
                        count += b.look(bots);
                    }
                    else if (b.getCode()[count] == 2) {//case(2): {
                        System.out.println("никого не видно");
                    }
                    else {//default: {
                        //System.out.println("BRUH " + b.getCode()[count]);
                        count++;
                    }
                }
            }
            //System.out.println("ezxrctvybubvrdvtsseresdxfda");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("BLYAT!");
            }
        }
    }
}
