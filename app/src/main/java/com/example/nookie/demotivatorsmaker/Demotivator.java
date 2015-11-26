package com.example.nookie.demotivatorsmaker;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public Bitmap toBitmap() {
        caption = "caption";
        text = "text";

        Bitmap output = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Bitmap scaledImage = scaleImage(image);
        Canvas c = new Canvas(output);
        if (paint==null)
            paint = new Paint();
        //background
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //c.drawPaint(paint);

        //c.drawBitmap(scaledImage,output.getWidth()/2-scaledImage.getWidth()/2,(float)dpToPx(Y_PADDING_DP),paint);

        c = placeText(c);

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"testdem.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            output.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    private Bitmap scaleImage(Bitmap image) {
        int originalW = image.getWidth();
        int originalH = image.getHeight();

        int width = DEFAULT_WIDTH - dpToPx(X_PADDING_DP)*2;
        int height = DEFAULT_HEIGHT - dpToPx(Y_PADDING_DP)*3;

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


    public String getText() {
        return text;
    }

    public String getCaption() {
        return caption;
    }

    private boolean hasText(){
        return getText().length()>0;
    }

    private boolean hasCaption(){
        return getCaption().length()>0;
    }

    private Canvas placeText(Canvas canvas){
        if (hasText()) {
            canvas.translate(100,50);
            TextPaint smallTP = new TextPaint();
            smallTP.setStyle(Paint.Style.STROKE);
            smallTP.setColor(Color.YELLOW);
            smallTP.setTextSize(10);
            StaticLayout smallTextSL = new StaticLayout(getText(),
                    smallTP,0, Layout.Alignment.ALIGN_CENTER,0,0,false);
            smallTextSL.draw(canvas);
            canvas.save();
            canvas.restore();
        }
        if (hasCaption()) {
            canvas.translate(200,50);
            TextPaint captionTP = new TextPaint();
            captionTP.setStyle(Paint.Style.STROKE);
            captionTP.setColor(Color.YELLOW);
            captionTP.setTextSize(20);
            StaticLayout captionSL = new StaticLayout(getText(),
                    captionTP,0, Layout.Alignment.ALIGN_CENTER,0,0,false);
            captionSL.draw(canvas);
            canvas.save();
            canvas.restore();
        }

        return canvas;
    }

}
