package com.example.cyberlife;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DrawThread extends Thread{
    private Context context;


    private final SurfaceHolder surfaceHolder;

    private static boolean clickF=false;
    private static byte stopGame=1;
    private static boolean settings_menu = false;
    private static int cy,cx;
    private volatile boolean running = true;//флаг для остановки потока


    private Bot infoBot=null;
    private short[] saveCode = new short[16];
    Paint background = new Paint();
    Paint background2 = new Paint();
    Paint paint = new Paint();
    Paint p = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    Paint p4 = new Paint();
    Paint p5 = new Paint();
    Paint b1 = new Paint();
    Paint b2 = new Paint();
    Paint b3 = new Paint();
    Paint b4 = new Paint();
    Paint settingP = new Paint();
    Paint offB = new Paint();
    Paint onB = new Paint();



    {
        background.setColor(Color.WHITE);
        background.setStyle(Paint.Style.FILL);
        background2.setColor(0xAA888888);
        background2.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        p2.setColor(Color.BLACK);
        p2.setStyle(Paint.Style.FILL);
        p2.setTextSize(54);
        p2.setColor(Color.WHITE);
        b1.setColor(0xFF00CC00);
        b1.setStyle(Paint.Style.FILL);

        b2.setColor(0xFF7F00FF);
        b2.setStyle(Paint.Style.FILL);
        p2.setStrokeWidth(4);

        p3.setTextSize(24);
        p3.setColor(Color.BLACK);

        p4.setTextSize(40);
        p4.setColor(0xAADFDFDF);

        p5.setTextSize(38);

        b3.setColor(0xFF00CC00);
        b3.setStyle(Paint.Style.FILL);
        b4.setColor(0xFFE61414);
        b4.setStyle(Paint.Style.FILL);

        settingP.setColor(0xFF777777);
        settingP.setStyle(Paint.Style.FILL);

        offB.setColor(0xAAA0A6AB);
        onB.setColor(0xAAFF8500);
    }

    public static void click(int x,int y){
        clickF=true;
        cx=x;
        cy=y;
    }


    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
    }
    public void requestStop() {
        running = false;
    }

    public ArrayList<Bot> createBots(){
        ArrayList<Bot> bots=new ArrayList<>();
        for (int i = 0; i < 10; i++) { //TODO kolichestvo botov
            int newx = (int)(new Random().nextInt(500) /50)*50;
            int newy = (int)(new Random().nextInt(1000)/50)*50;
            boolean f3=true;
            for (Bot j:bots) {
                if(j.getX()==newx && j.getY()==newy){
                    f3=false;
                    i--;

                }
            }
            if (f3){
                bots.add(new Bot(newx,newy));
            }

        }
        return bots;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    @Override
    public void run() {
        ArrayList<Bot> bots=new ArrayList<>();
        bots=createBots();
        bots.add(new Bot(500,500,new short[]{24,21,19,81,12,29,9,6,3,6,4,6,9,6,12,12},new Color()));
        bots.add(new Bot(0,0,new short[]{23,18,17,81,12,29,9,6,3,6,4,6,9,6,12,12},new Color()));

        //bots.add(bots.get(0).dublicate());
        /*for (int i = 0; i < 10; i++) {
                int newx = (int)(new Random().nextInt(500) /50)*50;
                int newy = (int)(new Random().nextInt(1000)/50)*50;
                boolean f3=true;
                for (Bot j:bots) {
                    if(j.getX()==newx && j.getY()==newy){
                        f3=false;
                        i--;

                    }
                }
                if (f3){
                    bots.add(new Bot(newx,newy));
                }

        }*/
        //System.out.println(bots.get(0).code());





        while (running) {
            for (int i=0;i<bots.size();i++) {
                if(stopGame!=0) {
                    bots.get(i).setEnergy((short) (bots.get(i).getEnergy() - 5));
                    //System.out.println(bots.get(i).toString() );
                    //System.out.println(bots.get(i).code());
                    if (bots.get(i).getEnergy() <= 0) {
                        bots.remove(bots.get(i));
                        i--;
                        continue;
                    }
                }

            }
            

            Canvas canvas = surfaceHolder.lockCanvas();
            byte k=0;
            if (stopGame!=0) {
                for (int bb = 0; bb < bots.size() - k; bb++) {
                    Bot b = bots.get(bb);

                    for (short count = 0; count < b.getCode().length; ) {
                        System.out.println(b.getCode()[count]);
                        //switch (b.getCode()[count]){
                        if (b.getCode()[count] == 17) {//case(17): {   //                      ГЕНЕРАЦИЯ
                            b.generate();
                            count++;
                            break;
                        } else if (b.getCode()[count] == 18) {//case(18): {   //                       ДВИЖЕНИЕ
                            b.move(canvas);
                            count++;
                            break;
                        } else if (b.getCode()[count] == 19) {//case(19): {   //
                            //if (b.getEnergy()>=50) {
                            boolean f4 = true;
                            for (Bot h : bots) {
                                if (h.getX() == b.getTarget()[0] && h.getY() == b.getTarget()[1] || b.getTarget()[0] < 0 || b.getTarget()[0] >= canvas.getWidth() || b.getTarget()[1] < 0 || b.getTarget()[1] >= canvas.getHeight() - 150) {
                                    f4 = false;
                                    break;
                                }
                            }
                            if (f4) {
                                bots.add(b.dublicate());
                                k++;
                                //System.out.println(b.getX() + " " + b.getY());
                                count++;
                                break;
                            } else count++;
                            //}
                        } else if (b.getCode()[count] == 20) {//case(20): {   //                      ПОЕДАНИЕ
                        /*System.out.println("=====================");
                        System.out.println(b.getX()+" "+b.getY());
                        System.out.println(b.getDx()+" "+b.getDy());
                        System.out.println(b.getCurrent()[0]+" "+b.getCurrent()[1]);
                        System.out.println("=====================");*/
                            for (int h = 0; h < bots.size(); h++) {
                                if (bots.get(h).getX() == b.getTarget()[0] || bots.get(h).getY() == b.getTarget()[1]) {
                                    System.out.println("SUCCESS!");
                                    b.eat(bots.get(h), bots);
                                    break;
                                }
                            }
                            count++;
                            break;
                        } else if (b.getCode()[count] == 21) {//case(21): {  //                       ПОВОРОТ ВЛЕВО
                            b.rotateL();
                            count++;
                            break;
                        } else if (b.getCode()[count] == 22) {//case(22): {   //                      ПОВОРОТ ВПРАВО
                            b.rotateR();
                            count++;
                            break;
                        } else if (b.getCode()[count] == 23) {//case(23): {   //                      ПРОВЕРКА ЭНЕРГИИ
                            count += b.energy();
                            //break;
                        } else if (b.getCode()[count] == 24) {//case(24):    //                      ПРОВЕРКА ВПЕРЕДИСТОЯЩЕГО
                            count += b.look(bots);
                            //break;
                        } else {//default: {
                            //System.out.println("BRUH " + b.getCode()[count]);
                            if (b.getCode()[count] != 0) {
                                count += b.getCode()[count];
                            } else break;
                        }
                    }
                }
            }

//========================================отрисовка===========================================

            if (canvas != null) {
                try {
                    canvas.drawRect(0,0, canvas.getWidth(),canvas.getHeight(),background);
                    //canvas.drawRect(0,canvas.getHeight()-150, canvas.getWidth(),canvas.getHeight(),background2);
                    for (Bot i:bots) {
                        i.draw(canvas,paint,p);
                    }
                    boolean f=false;
                    boolean f2=false;
                    PictureButton pause = new PictureButton(canvas.getWidth()-100, 0, 100,100,R.drawable.pause,context);
                    PictureButton boost = new PictureButton(canvas.getWidth()-100, 100, 100,100,R.drawable.boost,context);
                    PictureButton save = new PictureButton(20, canvas.getHeight()-125, 100,100,R.drawable.save,context);
                    PictureButton load = new PictureButton(140, canvas.getHeight()-125, 100,100,R.drawable.load,context);
                    PictureButton restart = new PictureButton(280,canvas.getHeight()-125, 100,100,R.drawable.restart,context);

                    if (clickF) {
                        //System.out.println(i+" 1234567890");
                        clickF = false;
                        f2 = true;
                        //----------проверка на выделение бота ------------
                        for (Bot i : bots) {
                            if (cx > i.getX() && cx < i.getX() + 50 && cy > i.getY() && cy < i.getY() + 50) {
                                infoBot = i;
                                f = true;
                                System.out.println(i);
                            }
                            System.out.println(infoBot);
                            System.out.println(cx + " / " + cy);
                        }
                        //-------------------------------

                        //------------проверка на нажатие кнопок------------------
                        if (cx > 20 && cx < 200 && cy > canvas.getHeight() - 125 && cy < canvas.getHeight() - 25) {// SAVE
                            if (infoBot != null) {
                                saveCode = infoBot.getCode();

                                File file;
                                try {
                                    file = new File(context.getFilesDir(),"code_.txt");
                                    if (!file.exists()) {
                                        file.createNewFile();
                                    }
                                    // Открытие потока для записи в файл
                                    FileOutputStream outputStream = new FileOutputStream(file,true);

                                    // Записываем строку "текст" в файл
                                    String data = "";
                                    for (short sh:saveCode) {
                                        data+=sh+" ";

                                    }
                                    data+="\n";
                                    outputStream.write(data.getBytes());

                                    // Закрытие потока записи
                                    outputStream.close();

                                } catch (FileNotFoundException e) {
                                    //Toast.makeText(context, "Ошибка FileNotFound", Toast.LENGTH_SHORT).show();
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    //Toast.makeText(context, "Ошибка какая-то", Toast.LENGTH_SHORT).show();
                                    throw new RuntimeException(e);
                                }


                            }
                        }
                        if (cx > 220 && cx < 400 && cy > canvas.getHeight() - 125 && cy < canvas.getHeight() - 25) {// Load
                            if (infoBot != null && saveCode != null) {
                                infoBot.setCode(saveCode);
                                /*runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyDialogFragment dialog = new MyDialogFragment();
                                        dialog.show(getFragmentManager(), "MyDialogFragment");
                                    }
                                });*/
                                //TODO load code from file
                            }
                        }
                        /*if (cx>canvas.getWidth()-100 && cy>canvas.getHeight()-150 && cy<canvas.getHeight()-75){
                            Bot.dEnergy+=5;
                        }
                        if (cx>canvas.getWidth()-100 && cy>canvas.getHeight()-75){
                            if (Bot.dEnergy>0) Bot.dEnergy-=5;
                        }*/
                        if (cx > canvas.getWidth() - 150 && cy > canvas.getHeight() - 150) {
                            settings_menu = !settings_menu;
                        }
                        if (pause.isClicked(cx,cy)){
                            stopGame= (byte) (stopGame==0?1:0);
                        }
                        if (boost.isClicked(cx,cy)){
                            stopGame= (byte) (stopGame==1 || stopGame==0? 2:1);
                        }
                        if (restart.isClicked(cx,cy)){
                            bots=createBots();
                        }
                    }

                    for (Bot i :bots) {
                        //i.draw(canvas,paint,p);
                        if (i==infoBot){
                            i.drawInfo(canvas);
                        }
                    }


                    if (!f && f2) infoBot=null;


                    canvas.drawRect(0,canvas.getHeight()-150, canvas.getWidth(),canvas.getHeight(),background2);
                    //canvas.drawRect(20,canvas.getHeight()-125, 200,canvas.getHeight()-25,b1);
                    //canvas.drawRect(220,canvas.getHeight()-125, 400,canvas.getHeight()-25,b2);
                    save.draw(canvas,0xFF00CC00);
                    load.draw(canvas,0xFF7F00FF);
                    restart.draw(canvas,0xFFFFFFFF);
                    pause.draw(canvas,stopGame==0?0xAAFF8500:0xAAA0A6AB);
                    boost.draw(canvas,stopGame==2?0xAAFF8500:0xAAA0A6AB);

                    //canvas.drawText("Save",50,canvas.getHeight()-80,p2);
                    //canvas.drawText("Load",250,canvas.getHeight()-80,p2);

                    /*canvas.drawRect(canvas.getWidth()-100,canvas.getHeight()-150,canvas.getWidth(),canvas.getHeight()-75,b3);
                    canvas.drawRect(canvas.getWidth()-100,canvas.getHeight()-75,canvas.getWidth(),canvas.getHeight(),b4);
                    canvas.drawText("+",canvas.getWidth()-60,canvas.getHeight()-100,p3);
                    canvas.drawText("-",canvas.getWidth()-60,canvas.getHeight()-40,p3);
                    canvas.drawText(Bot.dEnergy+"",canvas.getWidth()-60,canvas.getHeight()-66,p3);*/

                    //canvas.drawRect(canvas.getWidth()-150,canvas.getHeight()-150,canvas.getWidth(),canvas.getHeight(),settingP);Drawable sett =
                    Bitmap sett = drawableToBitmap(context.getResources().getDrawable(R.drawable.setting));
                    sett = Bitmap.createScaledBitmap(sett, 150, 150,true);
                    canvas.drawBitmap(sett,canvas.getWidth()-150,canvas.getHeight()-150,p);




                    /*String text="";
                    for (int j = 0; j < 4; j++) {
                        text = "";
                        for (int i = j * 4; i < (j + 1) * 4; i++) {
                            text = text + " " + (saveCode[i] < 10 ? "  " + saveCode[i] : saveCode[i]);
                        }
                        canvas.drawText(text, 425, canvas.getHeight() - 90 + j * 19, p3);
                    }*/

                    // меню настроек
                    if (settings_menu){
                        canvas.drawRect(canvas.getWidth()-400,canvas.getHeight()-650, canvas.getWidth(),canvas.getHeight()-150,background2);
                        canvas.drawText("Sun",canvas.getWidth()-390,canvas.getHeight()-620,p3);
                        canvas.drawLine(canvas.getWidth()-250,canvas.getHeight()-630,canvas.getWidth()-50,canvas.getHeight()-630,p2);
                        canvas.drawText("Mutation",canvas.getWidth()-390,canvas.getHeight()-580,p3);
                        canvas.drawLine(canvas.getWidth()-250,canvas.getHeight()-590,canvas.getWidth()-50,canvas.getHeight()-590,p2);
                        canvas.drawText("Start energy",canvas.getWidth()-390,canvas.getHeight()-540,p3);
                        canvas.drawLine(canvas.getWidth()-250,canvas.getHeight()-550,canvas.getWidth()-50,canvas.getHeight()-550,p2);
                    }

                    /*for (int i = 0; i<saveCode.length;i++) {
                        text+=" "+saveCode[i];
                        if (i%8==0){
                            canvas.drawText(text,450,canvas.getHeight()-(80-i*15),p);
                            text="";
                        }
                    }*/
                    //canvas.drawText(text,500,canvas.getHeight()-80,p);




                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
//========================================отрисовка===========================================
            try {
                if (stopGame==2) Thread.sleep(100);
                else Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("BLYAT!");
            }
        }
    }
}
