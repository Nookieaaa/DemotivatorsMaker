package com.nookdev.maker.dem.models;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.nookdev.maker.dem.App;

public class Demotivator {
    private static final int THUMBNAIL_DEFAULT_WIDTH_DP = 150;
    private Bitmap image;
    private String caption;
    private String text;
    private Paint paint;


    public static final String TIMES_NEW_ROMAN_PATH = "fonts/Times_New_Roman.ttf";
    public static final String TIMES_NEW_ROMAN_BOLD_PATH = "fonts/Times_New_Roman_Bold.ttf";

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    public static final int X_PADDING_DP = 40;//10%
    public static final int Y_PADDING_DP = 50;//6%

    public static final int BORDER_WIDTH = 5;
    public static final int BORDER_MARGIN = 10;

    public static final int CAPTION_SP_SIZE = 60;
    public static final int TEXT_SP_SIZE = 30;

    public Demotivator(Bitmap image, String caption, String text) {
        if (image==null){
            this.image = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
        }
        else {
            this.image = image;
        }
        this.caption = caption;
        this.text = text;
    }

    public Bitmap toBitmap() {

        StaticLayout smallTextSL = null;
        StaticLayout captionSL = null;

        Bitmap scaledImage = scaleImage(image);

        int calculatedHeight = scaledImage.getHeight() + (int)(dpToPx(Y_PADDING_DP)*2)
                +dpToPx(BORDER_WIDTH)*2+dpToPx(BORDER_MARGIN)*2;



        if (hasText()){
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            Typeface tnr = Typeface.createFromAsset(App.getAppContext().getAssets(),TIMES_NEW_ROMAN_PATH);
            textPaint.setTypeface(tnr);

            textPaint.setTextSize(getTextSize());

            smallTextSL = new StaticLayout(getText(),
                    textPaint,scaledImage.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,true);
            calculatedHeight += smallTextSL.getHeight();
        }

        if (hasCaption()){
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
            Typeface tnrb = Typeface.createFromAsset(App.getAppContext().getAssets(),TIMES_NEW_ROMAN_BOLD_PATH);
            textPaint.setTypeface(tnrb);
            textPaint.setTextSize(getCaptionTextSize());
            captionSL = new StaticLayout(getCaption(),
                    textPaint,scaledImage.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,true);
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
                dpToPx(X_PADDING_DP)-dpToPx(BORDER_MARGIN)-dpToPx(BORDER_WIDTH),
                dpToPx(Y_PADDING_DP)-dpToPx(BORDER_MARGIN)-dpToPx(BORDER_WIDTH),
                scaledImage.getWidth()+dpToPx(X_PADDING_DP) +dpToPx(BORDER_MARGIN)+dpToPx(BORDER_WIDTH),
                scaledImage.getHeight()+dpToPx(Y_PADDING_DP) +dpToPx(BORDER_MARGIN)+dpToPx(BORDER_WIDTH));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(dpToPx(BORDER_WIDTH));
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

        return output;
    }

    public static Bitmap scaleImage(Bitmap image) {
        int originalW = image.getWidth();
        int originalH = image.getHeight();

        int width = DEFAULT_WIDTH;

        float ratio = width / (float) originalW;

        int height = (int)(originalH * ratio);

        return Bitmap.createScaledBitmap(image,width,height,false);
    }

    public static Bitmap makeThumbnail(Bitmap image,int widthDP) {
        int originalW = image.getWidth();
        int originalH = image.getHeight();
        int width = 0;

        if (widthDP==0)
            width = dpToPx(THUMBNAIL_DEFAULT_WIDTH_DP);
        else{
            width = dpToPx(widthDP);
        }

        float ratio = width / (float) originalW;

        int height = (int)(originalH * ratio);

        return Bitmap.createScaledBitmap(image,width,height,false);
    }

    private float getCaptionTextSize(){
        return spToPx(CAPTION_SP_SIZE);
    }

    private float getTextSize(){
        return spToPx(TEXT_SP_SIZE);
    }

    private float spToPx(int sp){
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    private static int dpToPx(int dp){
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
