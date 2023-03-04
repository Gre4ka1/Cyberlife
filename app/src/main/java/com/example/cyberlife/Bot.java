package com.example.cyberlife;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import java.util.ArrayList;
import java.util.Random;

public class Bot{
    private int x,y;
    private int dx,dy;
    private short[] code;
    private Color color;
    private short energy;

    public Bot(int x, int y, short[] code, Color color) {
        this.x = x;
        this.y = y;
        this.code = code;
        this.color = color;
        this.dx= (new Random().nextInt(2)-1)*50;
        this.dy= (new Random().nextInt(2)-1)*50;
        if (dx==0 && dy==0) dx = 50;
        this.energy=50;
    }

    public Bot(int x, int y) {
        this.x = x;
        this.y = y;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.color = Color.valueOf((int)(Math.random() * 0x1000000));
        }
        this.dx= (new Random().nextInt(2)-1)*50;
        this.dy= (new Random().nextInt(2)-1)*50;
        if (dx==0 && dy==0) dx = 50;
        this.energy=50;
        this.code= new short[16];
        for (int i = 0; i < code.length; i++) {
            code[i]=(short) new Random().nextInt(32);
        }
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawRect(x,y, (x+50),(y+50),paint);
    }
    public void move(Canvas canvas){
        //System.out.println(x+" "+y);
        x=x+dx;
        y=y+dy;
        if(x+50>=canvas.getWidth() || x<=0) dx=dx*-1;
        if(y+50>=canvas.getHeight() || y<=0) dy=dy*-1;
        //System.out.println("d: "+dx+" "+dy);
        //System.out.println(x+" "+y);
    }
    public void generate(){
        energy+=25;
        if (energy>100) energy=100;
    }
    public Bot dublicate(){
        return new Bot(x+dx,y+dy,code,color);
    }
    public void eat(Bot enemy, ArrayList<Bot> bots){
        bots.remove(enemy);
        energy+=enemy.energy*0.75;
        if (energy>100) energy=100;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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

    public int[] getCurrent(){
        return new int[]{x+dx, y+dy};
    }
}

