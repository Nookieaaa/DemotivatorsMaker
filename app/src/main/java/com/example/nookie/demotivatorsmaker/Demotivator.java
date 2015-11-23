package com.example.nookie.demotivatorsmaker;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Demotivator {
    private Bitmap image;
    private String caption;
    private String text;
    private Paint paint;

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static final int X_PADDING_DP = 20;
    public static final int Y_PADDING_DP = 20;

    public Demotivator(Bitmap image, String caption, String text) {
        this.image = image;
        this.caption = caption;
        this.text = text;
    }

    public Bitmap toBitmap(){
        Bitmap output = Bitmap.createBitmap(DEFAULT_WIDTH,DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Bitmap scaledImage = scaleImage(image);
        Canvas c = new Canvas(output);
        if (paint==null)
            paint = new Paint();
        //background
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        c.drawPaint(paint);

        c.drawBitmap(scaledImage,(float)dpToPx(X_PADDING_DP),(float)dpToPx(Y_PADDING_DP),paint);



        return output;
    }

    private Bitmap scaleImage(Bitmap image) {
        int originalW = image.getWidth();
        int originalH = image.getHeight();

        int width = DEFAULT_WIDTH - dpToPx(X_PADDING_DP)*2;
        int height = DEFAULT_HEIGHT - dpToPx(Y_PADDING_DP)*2;

        float ratio = originalW / (float) originalH;

        if (ratio<1){
            width = (int)(originalH * ratio);
        }
        else{
            height = (int)(originalW/ratio);
        }

        return Bitmap.createScaledBitmap(image,width,height,false);
    }

    private int pxToDp(int px){
        return (int)(px / Resources.getSystem().getDisplayMetrics().density);
    }

    private int dpToPx(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
