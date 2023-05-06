package com.example.cyberlife;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bot{
    private int x,y;
    private int dx,dy;
    private final int size=50;
    private short[] code;
    private int color;
    private short energy;

    private Paint paint = new Paint();
    private Paint p = new Paint();
    public static int dEnergy=25;
    public static int mutation=10;  // процент на изменение каждой цифры кода(в тысячных)



    public Bot(int x, int y, short[] code, int color) {
        this.x = x;
        this.y = y;
        this.code = code;
        this.color = color;
        this.dx= (new Random().nextInt(2)-1)*size;
        this.dy= (new Random().nextInt(2)-1)*size;
        if (dx==0 && dy==0) dx = size;
        this.energy=40;
        p.setColor(0xFFFFFFFF);
        //BotsRepository.getInstance().addBot(this);
    }

    public Bot(int x, int y) {
        this.x = x;
        this.y = y;
        Random r=new Random();
        int col=((r.nextInt(155)+100) << 24)+((r.nextInt(255)) << 16)+(r.nextInt(255) << 8)+r.nextInt(255);
        this.color=col;
        p.setColor(0xFFFFFFFF);


        this.dx= (new Random().nextInt(2)-1)*size;
        this.dy= (new Random().nextInt(2)-1)*size;
        if (dx==0 && dy==0) dx = size;
        this.energy=50;
        this.code= new short[16];
        for (int i = 0; i < code.length; i++) {
            code[i]= (short) ((short) new Random().nextInt(20)+1);
        }
        //BotsRepository.getInstance().addBot(this);
    }

    public void draw(Canvas canvas){
        paint.setColor(color);
        canvas.drawRect(x,y, (x+size),(y+size),paint);
        int xx=dx<0?x:dx==0?x+22:x+45,yy=dy<0?y:dy==0?y+22:y+45;
        canvas.drawRect(xx,yy, (xx+5),(yy+5),p);
    }

    public boolean clickCheck(int cx, int cy){
        if (cx>x && cx<x+size && cy>y && cy<y+size){
            return true;
        }
        return false;
    }
    public void drawInfo(Canvas canvas){
        Paint text = new Paint();
        text.setStyle(Paint.Style.FILL);
        text.setColor(Color.BLACK);
        text.setTextSize(16);
        String cod;
        for (int j = 0; j < 4; j++) {
            cod="";
            for (int i = j*4; i < (j+1)*4; i++) {
                cod=cod+" "+(code[i]<10?"  "+code[i]:code[i]);
            }
            canvas.drawText(cod,x+(int)(size/2),y+(int)(size/2)+j*20,text);
        }

    }
    public boolean minusEnergy(){
        energy-=Math.round(dEnergy/4);
        if (energy<=0) return true;
        return false;
    }
    public void move(Canvas canvas){
        //System.out.println(x+" "+y);
        if(getTarget()[0]>=canvas.getWidth() || getTarget()[0]<0) dx=dx*-1;
        if(getTarget()[1]>=canvas.getHeight() || getTarget()[1]<0) dy=dy*-1;
        x=x+dx;
        y=y+dy;
        energy-=10;
        //System.out.println("d: "+dx+" "+dy);
        //System.out.println(x+" "+y);
    }
    public void generate(){
        energy+=dEnergy;
        if (energy>100) energy=100;
    }
    public Bot dublicate(){//TODO изменение цвета в связи с изменением кода
        energy-=50;
        short[] newCode= new short[16];
        for (int i=0;i<16;i++) {
            int a = new Random().nextInt(1000);
            if (a>=mutation) {
                newCode[i] = code[i];
            }
            else newCode[i] = (short) (new Random().nextInt(21));
        }
        return new Bot(x+dx,y+dy,newCode,color);
    }
    public void eat(Bot enemy, ArrayList<Bot> bots){

        bots.remove(enemy);
        energy+=enemy.energy*0.75;
        if (energy>100) energy=100;
    }
    public void rotateL(){
        //System.out.println(dx+" "+dy);
        if (energy>=5) {
            if (dx == size && dy == -1*size) {
                dx = 0;
                dy = -1*size;
            }
            else if (dx == 0 && dy == -1*size) {
                dx = -1*size;
                dy = -1*size;
            }
            else if (dx == -1*size && dy == -1*size) {
                dx = -1*size;
                dy = 0;
            }
            else if (dx == -1*size && dy == 0) {
                dx = -1*size;
                dy = size;
            }
            else if (dx == -1*size && dy == size) {
                dx = 0;
                dy = size;
            }
            else if (dx == 0 && dy == size) {
                dx = size;
                dy = size;
            }
            else if (dx == size && dy == size) {
                dx = size;
                dy = 0;
            }
            else if (dx == size && dy == 0) {
                dx = size;
                dy = -1*size;
            }
            energy-=5;
        }
    }
    public void rotateR(){
        if (energy>=5){
            if (dx==size && dy ==-1*size){ dx=size; dy=0;}
            else if (dx==size && dy ==0) {dx=size; dy=size;}
            else if (dx==size && dy ==size) {dx=0; dy=size;}
            else if (dx==0 && dy ==size) {dx=-1*size; dy=size;}
            else if (dx==-1*size && dy ==size) {dx=-1*size; dy=0;}
            else if (dx==-1*size && dy ==0) {dx=-1*size; dy=-1*size;}
            else if (dx==-1*size && dy ==-1*size) {dx=0; dy=-1*size;}
            else if (dx==0 && dy ==-1*size) {dx=size; dy=-1*size;}
            energy-=5;
        }
    }
    public short energy(){
        if (energy>50) return (short) (1);
        return (short) (2);
    }
    public short look( ArrayList<Bot> bots){
        for (Bot b:bots) {
            if(b.getX()==x+dx && b.getY()==y+dy){
                //TODO check enemy/friend
                return (short) (1);
            }
        }
        return (short) (2);
    }

    public ArrayList<Bot>[] runCode(Canvas canvas,ArrayList<Bot>[] tempL){

        for (short count = 0; count < code.length;) {
            if (code[count] == 13) {   //                      ГЕНЕРАЦИЯ
                generate();
                count++;
                break;
            }
            else if (code[count] == 14) {//                       ДВИЖЕНИЕ
                move(canvas);
                count++;
                break;
            }
            else if (code[count] == 15) {//                         размножение
                if (energy >= 50) {
                    boolean f4 = true;
                    for (Bot h : BotsRepository.getInstance().getBots()) {
                        if (h.getX() == getTarget()[0] && h.getY() == getTarget()[1] || getTarget()[0] < 0 || getTarget()[0] >= canvas.getWidth() || getTarget()[1] < 0 || getTarget()[1] >= canvas.getHeight()) {
                            f4 = false;
                            break;
                        }
                    }
                    if (f4) {
                        //BotsRepository.getInstance().addBot(dublicate());
                        tempL[0].add(dublicate());
                        //k++;
                        //System.out.println(b.getX() + " " + b.getY());
                        count++;
                        break;
                    } else count++;
                } else count++;
            }
            else if (code[count] == 16) {//                      ПОЕДАНИЕ
                for (int h = 0; h < BotsRepository.getInstance().getBots().size(); h++) {
                    if (BotsRepository.getInstance().getBots().get(h).getX() == getTarget()[0] || BotsRepository.getInstance().getBots().get(h).getY() == getTarget()[1]) {
                        //System.out.println("SUCCESS!");
                        tempL[1].add(BotsRepository.getInstance().getBots().get(h));
                        //eat(BotsRepository.getInstance().getBots().get(h), BotsRepository.getInstance().getBots());
                        break;
                    }
                }
                count++;
                break;
            }
            else if (code[count] == 17) {//                 ПОВОРОТ ВЛЕВО
                rotateL();
                count++;

            } else if (code[count] == 18) {//                 ПОВОРОТ ВПРАВО
                rotateR();
                count++;

            } else if (code[count] == 19) {//                 ПРОВЕРКА ЭНЕРГИИ
                count += energy();
            } else if (code[count] == 20) {//                 ПРОВЕРКА ВПЕРЕДИСТОЯЩЕГО
                count += look(BotsRepository.getInstance().getBots());
            } else {//default: {
                //System.out.println("BRUH " + b.getCode()[count]);
                if (code[count] != 0) {
                    count += code[count];
                } else break;
            }
        }
        return tempL;
    }



    public int getDx() {
        return dx;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }
    public int getDy() {
        return dy;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }
    public short[] getCode() {
        return code;
    }
    public void setCode(short[] code) {
        this.code = code;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public short getEnergy() {
        return energy;
    }
    public void setEnergy(short energy) {
        this.energy = energy;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int[] getTarget(){
        return new int[]{x+dx, y+dy};
    }
    @Override
    public String toString() {
        return "Bot{" +
                "x=" + x +
                ", y=" + y +
                ", energy=" + energy +
                '}';
    }
    public String printCode(){
        String text="";
        for (short t:code) {
            text+=" "+t;
        }
        return text;
    }
}

