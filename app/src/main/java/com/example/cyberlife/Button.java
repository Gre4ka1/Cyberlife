package com.example.cyberlife;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Button {
    private int x,y;
    private int dx,dy;
    private boolean circle;
    private int centerX,centerY;
    private int radius;

    public Button(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        circle=false;
    }

    public Button(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        circle=true;
    }

    public void draw(Canvas canvas, int color){
        Paint p = new Paint();
        p.setColor(color);
        if (circle){
            canvas.drawCircle(centerX,centerY,radius,p);
        }
        else {
            canvas.drawRect(x, y, x + dx, y + dy, p);
        }
    }

    public boolean isClicked(int cx, int cy){
        if (circle){
            if (cx>=centerX-radius && cx<= centerX+radius && cy>= centerY-radius && cy<= centerY+radius){
                return true;
            }
        } else {
            if (cx >= x && cx <= x + dx && cy >= y && cy <= y + dy) {
                return true;
            }
        }
        return false;
    }
}
