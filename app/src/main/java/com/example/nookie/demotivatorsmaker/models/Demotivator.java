package com.example.nookie.demotivatorsmaker.models;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

    public static final int X_PADDING_DP = 40;//10%
    public static final int Y_PADDING_DP = 20;//6%

    public static final int BORDER_WIDTH = 5;
    public static final int BORDER_MARGIN = 10;

    public Demotivator(Bitmap image, String caption, String text) {
        this.image = image;
        this.caption = caption;
        this.text = text;
    }

    public Bitmap toBitmap() {
        StaticLayout smallTextSL = null;
        StaticLayout captionSL = null;


        Bitmap scaledImage = scaleImage(image);

        int calculatedHeight = scaledImage.getHeight() + dpToPx(Y_PADDING_DP)*3;

        if (hasText()){
            TextPaint smallTP = new TextPaint();
            smallTP.setColor(Color.WHITE);
            smallTP.setTextSize(40);
            smallTextSL = new StaticLayout(getText(),
                    smallTP,scaledImage.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,true);
            calculatedHeight += smallTextSL.getHeight();
        }

        if (hasCaption()){
            TextPaint captionTP = new TextPaint();
            captionTP.setColor(Color.WHITE);
            captionTP.setTextSize(65);
            captionSL = new StaticLayout(getCaption(),
                    captionTP,scaledImage.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,false);
            calculatedHeight += captionSL.getHeight();
        }


        Bitmap output = Bitmap.createBitmap((int)(scaledImage.getWidth()+dpToPx(X_PADDING_DP)*2),
                calculatedHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(output);
        if (paint==null)
            paint = new Paint();
        //background
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        c.drawPaint(paint);

        RectF border = new RectF(
                pxToDp(X_PADDING_DP)+20,
                pxToDp(Y_PADDING_DP),
                scaledImage.getWidth()+pxToDp(X_PADDING_DP)+50,
                scaledImage.getHeight()+pxToDp(Y_PADDING_DP)+30);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(BORDER_WIDTH);
        c.drawRect(border,paint);

        c.drawBitmap(scaledImage, output.getWidth() / 2 - scaledImage.getWidth() / 2,
                (float) dpToPx(Y_PADDING_DP), paint);

        c.translate(dpToPx(X_PADDING_DP),c.getHeight()-dpToPx(Y_PADDING_DP));

        if (smallTextSL!=null) {
            c.translate(0,- smallTextSL.getHeight());
            smallTextSL.draw(c);
        }
        if (captionSL!=null){

            c.translate(0, -captionSL.getHeight());
            captionSL.draw(c);
        }

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

        int width = DEFAULT_WIDTH;
        //int height = DEFAULT_HEIGHT - dpToPx(Y_PADDING_DP)*2;

        float ratio = width / (float) originalW;

        int height = (int)(originalH * ratio);

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

}
