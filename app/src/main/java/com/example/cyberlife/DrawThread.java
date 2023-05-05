package com.example.cyberlife;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;


import java.util.ArrayList;
import java.util.Random;

public class DrawThread extends Thread{
    private Context context;


    private final SurfaceHolder surfaceHolder;

    private static byte stopGame=1;
    private static boolean settings_menu = false;
    private static int cy,cx;
    private volatile boolean running = true;//флаг для остановки потока
    private CodeRepository repositiry = CodeRepository.getInstance();

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

    /*public static void click(int x,int y){
        clickF=true;
        cx=x;
        cy=y;
    }*/
    public static void pause(){
        stopGame = (byte) (stopGame==0?1:0);
    }
    public static void boost(){
        stopGame = (byte) (stopGame==2?1:2);
    }


    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
    }
    public void requestStop() {
        running = false;
    }
    public static void  createBots(Canvas canvas){
        //ArrayList<Bot> bots=new ArrayList<>();
        for (int i = 0; i < 10; i++) { //TODO kolichestvo botov
            int newx = (int)(new Random().nextInt(canvas.getWidth()) /50)*50;
            int newy = (int)(new Random().nextInt(canvas.getHeight())/50)*50;

            boolean f=true;
            for (Bot j:BotsRepository.getInstance().getBots()) {
                if(j.clickCheck(newx,newy)){
                    f=false;
                    i--;
                }
            }
            if (f){
                BotsRepository.getInstance().addBot(new Bot(newx,newy));
            }

        }
    }
    public static void createBots(){
        //ArrayList<Bot> bots=new ArrayList<>();
        for (int i = 0; i < 10; i++) { //TODO kolichestvo botov
            int newx = (int)(new Random().nextInt(500) /50)*50;
            int newy = (int)(new Random().nextInt(1000)/50)*50;

            boolean f=true;
            for (Bot j:BotsRepository.getInstance().getBots()) {
                if(j.clickCheck(newx,newy)){
                    f=false;
                    i--;
                }
            }
            if (f){
               BotsRepository.getInstance().addBot(new Bot(newx,newy));
            }

        }
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
        //ArrayList<Bot> bots=new ArrayList<>();
        BotsRepository.getInstance().addBot(new Bot(500,500,new short[]{19,2,13,20,17,15,9,6,3,6,4,6,9,6,12,12},0xFFFF0000));
        BotsRepository.getInstance().addBot(new Bot(0,0,new short[]{13,18,17,81,12,29,9,6,3,6,4,6,9,6,12,12},0xFFFF00FF));
        createBots();

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
            //BotsRepository.getInstance().setBots(bots);
            System.out.println(BotsRepository.getInstance().getBots().size());
            if (BotsRepository.getInstance().getBots().size()>1) {
                for (int i = 0; i < BotsRepository.getInstance().getBots().size(); i++) {
                    if (stopGame != 0) {
                        if (BotsRepository.getInstance().getBots().get(i).minusEnergy()){
                            BotsRepository.getInstance().getBots().remove(BotsRepository.getInstance().getBots().get(i));
                            i--;
                        }
                    }
                }
            } else if (BotsRepository.getInstance().getBots().size()==1) {
                if (stopGame != 0) {
                    if (BotsRepository.getInstance().getBots().get(0).minusEnergy()){
                        BotsRepository.getInstance().getBots().remove(BotsRepository.getInstance().getBots().get(0));
                    }
                }
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            if (ClickRepository.getInstance().getRestart()){
                createBots(canvas);
                ClickRepository.getInstance().setRestart(false);
            }

            byte k=0;
            if (stopGame!=0) {
                ArrayList<Bot> tempbots=BotsRepository.getInstance().getBots();
                for (Bot bot:tempbots) {
                    bot.runCode(canvas);
                }
                /*for (int bb = 0; bb < bots.size() - k; bb++) {
                    Bot b = bots.get(bb);

                    for (short count = 0; count < b.getCode().length; ) {

                        if (b.getCode()[count] == 13) {   //                      ГЕНЕРАЦИЯ
                            b.generate();
                            count++;
                            break;
                        } else if (b.getCode()[count] == 14) {//                       ДВИЖЕНИЕ
                            b.move(canvas);
                            count++;
                            break;
                        } else if (b.getCode()[count] == 15) {//
                            if (b.getEnergy()>=50) {
                                boolean f4 = true;
                                for (Bot h : bots) {
                                    if (h.getX() == b.getTarget()[0] && h.getY() == b.getTarget()[1] || b.getTarget()[0] < 0 || b.getTarget()[0] >= canvas.getWidth() || b.getTarget()[1] < 0 || b.getTarget()[1] >= canvas.getHeight()) {
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
                            } else count++;
                        } else if (b.getCode()[count] == 16) {//                      ПОЕДАНИЕ
                            for (int h = 0; h < bots.size(); h++) {
                                if (bots.get(h).getX() == b.getTarget()[0] || bots.get(h).getY() == b.getTarget()[1]) {
                                    //System.out.println("SUCCESS!");
                                    b.eat(bots.get(h), bots);
                                    break;
                                }
                            }
                            count++;
                            break;
                        } else if (b.getCode()[count] == 17) {//                 ПОВОРОТ ВЛЕВО
                            b.rotateL();
                            count++;

                        } else if (b.getCode()[count] == 18) {//                 ПОВОРОТ ВПРАВО
                            b.rotateR();
                            count++;

                        } else if (b.getCode()[count] == 19) {//                 ПРОВЕРКА ЭНЕРГИИ
                            count += b.energy();
                        } else if (b.getCode()[count] == 20) {//                 ПРОВЕРКА ВПЕРЕДИСТОЯЩЕГО
                            count += b.look(bots);
                        } else {//default: {
                            //System.out.println("BRUH " + b.getCode()[count]);
                            if (b.getCode()[count] != 0) {
                                count += b.getCode()[count];
                            } else break;
                        }
                    }
                }*/
            }

//========================================отрисовка===========================================

            if (canvas != null) {
                try {
                    canvas.drawRect(0,0, canvas.getWidth(),canvas.getHeight(),background);

                    ClickRepository instance = ClickRepository.getInstance();
                    if (instance.getClickX()!=null) {
                        cx = instance.getClickX();
                        cy = instance.getClickY();
                        instance.setClickX(null);
                        instance.setClickX(null);
                    }
                    //----------проверка на выделение бота ------------
                    boolean notEmptyFlag=false;
                    for (Bot i : BotsRepository.getInstance().getBots()) {
                        if (i.clickCheck(cx,cy)) {
                            CodeRepository.getInstance().updateCode(i.getCode());
                            infoBot = i;
                            i.setEnergy((short) 100);
                            notEmptyFlag = true;
                        }
                    }
                    if (!notEmptyFlag) infoBot=null;

                    for (Bot i:BotsRepository.getInstance().getBots()) {
                        i.draw(canvas);
                    }
                    if (infoBot != null) infoBot.drawInfo(canvas);


                    /*PictureButton pause = new PictureButton(canvas.getWidth()-100, 0, 100,100,R.drawable.pause,context);
                    PictureButton boost = new PictureButton(canvas.getWidth()-100, 100, 100,100,R.drawable.boost,context);
                    PictureButton save = new PictureButton(20, canvas.getHeight()-125, 100,100,R.drawable.save,context);
                    PictureButton load = new PictureButton(140, canvas.getHeight()-125, 100,100,R.drawable.load,context);
                    PictureButton restart = new PictureButton(280,canvas.getHeight()-125, 100,100,R.drawable.restart,context);




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
                                infoBot.setCode(saveCode);*/
                    /*activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DialogFragment dialog = new DialogFragment();
                                        dialog.show(activity.getFragmentManager(),"byvbuv");
                                    }
                                });

                            }
                        }

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
                    //}

                    //canvas.drawRect(0,canvas.getHeight()-150, canvas.getWidth(),canvas.getHeight(),background2);
                    //canvas.drawRect(20,canvas.getHeight()-125, 200,canvas.getHeight()-25,b1);
                    //canvas.drawRect(220,canvas.getHeight()-125, 400,canvas.getHeight()-25,b2);
                    save.draw(canvas,0xFF00CC00);
                    load.draw(canvas,0xFF7F00FF);
                    restart.draw(canvas,0xFFFFFFFF);
                    pause.draw(canvas,stopGame==0?0xAAFF8500:0xAAA0A6AB);
                    boost.draw(canvas,stopGame==2?0xAAFF8500:0xAAA0A6AB);

                    //canvas.drawText("Save",50,canvas.getHeight()-80,p2);
                    //canvas.drawText("Load",250,canvas.getHeight()-80,p2);

                    canvas.drawRect(canvas.getWidth()-100,canvas.getHeight()-150,canvas.getWidth(),canvas.getHeight()-75,b3);
                    canvas.drawRect(canvas.getWidth()-100,canvas.getHeight()-75,canvas.getWidth(),canvas.getHeight(),b4);
                    canvas.drawText("+",canvas.getWidth()-60,canvas.getHeight()-100,p3);
                    canvas.drawText("-",canvas.getWidth()-60,canvas.getHeight()-40,p3);
                    canvas.drawText(Bot.dEnergy+"",canvas.getWidth()-60,canvas.getHeight()-66,p3);

                    //canvas.drawRect(canvas.getWidth()-150,canvas.getHeight()-150,canvas.getWidth(),canvas.getHeight(),settingP);Drawable sett =
                    Bitmap sett = drawableToBitmap(context.getResources().getDrawable(R.drawable.setting));
                    sett = Bitmap.createScaledBitmap(sett, 150, 150,true);
                    canvas.drawBitmap(sett,canvas.getWidth()-150,canvas.getHeight()-150,p);




                    String text="";
                    for (int j = 0; j < 4; j++) {
                        text = "";
                        for (int i = j * 4; i < (j + 1) * 4; i++) {
                            text = text + " " + (saveCode[i] < 10 ? "  " + saveCode[i] : saveCode[i]);
                        }
                        canvas.drawText(text, 425, canvas.getHeight() - 90 + j * 19, p3);
                    }

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

                    for (int i = 0; i<saveCode.length;i++) {
                        text+=" "+saveCode[i];
                        if (i%8==0){
                            canvas.drawText(text,450,canvas.getHeight()-(80-i*15),p);
                            text="";
                        }
                    }
                    //canvas.drawText(text,500,canvas.getHeight()-80,p);*/
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                if (stopGame==2) Thread.sleep(100);
                else Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Thread sleep error!");
            }
        }
    }
}
