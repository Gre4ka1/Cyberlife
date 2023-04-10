package com.example.cyberlife;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

public class PictureButton extends Button{
    private Bitmap pict;
    public PictureButton(int x, int y, int dx, int dy, @DrawableRes int id, Context context) {
        super(x,y,dx,dy);
        pict = drawableToBitmap(context.getResources().getDrawable(id));
        pict = Bitmap.createScaledBitmap(pict, dx, dy,true);
    }

    @Override
    public void draw(Canvas canvas, int color) {
        Paint p2 = new Paint();
        p2.setColor(Color.RED);
        canvas.drawBitmap(pict,super.getX(),super.getY(),p2);
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
}
