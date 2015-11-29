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

    public static final int X_PADDING_DP = 40;//10%
    public static final int Y_PADDING_DP = 20;//6%

    public Demotivator(Bitmap image, String caption, String text) {
        this.image = image;
        this.caption = caption;
        this.text = text;
    }

    public Bitmap toBitmap() {
        caption = "Caption";
        text = "text text text text text text ";

        Bitmap scaledImage = scaleImage(image);

        Bitmap output = Bitmap.createBitmap(scaledImage.getWidth()*2, scaledImage.getHeight()*2, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(output);
        if (paint==null)
            paint = new Paint();
        //background
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        c.drawPaint(paint);

        c.drawBitmap(scaledImage, output.getWidth() / 2 - scaledImage.getWidth() / 2, (float) dpToPx(Y_PADDING_DP), paint);

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
        int height = DEFAULT_HEIGHT - dpToPx(Y_PADDING_DP)*2;

        float ratio = originalH / (float) originalW;

        if (ratio<1){
            height = (int)(originalW * ratio);
        }
        else{
            width = (int)(originalH/ratio);
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

        int restorePoint = canvas.save();
        if (hasText()) {
            TextPaint smallTP = new TextPaint();
            smallTP.setColor(Color.WHITE);
            smallTP.setTextSize(40);

            StaticLayout smallTextSL = new StaticLayout(getText(),
                    smallTP,canvas.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,true);

            canvas.translate(0, canvas.getHeight() - smallTextSL.getHeight());
            smallTextSL.draw(canvas);
        }
        if (hasCaption()) {
            TextPaint captionTP = new TextPaint();
            captionTP.setColor(Color.WHITE);
            captionTP.setTextSize(65);
            StaticLayout captionSL = new StaticLayout(getCaption(),
                    captionTP,canvas.getWidth(), Layout.Alignment.ALIGN_CENTER,1,1,false);

            canvas.translate(0, (hasText() ? 0 : canvas.getHeight())
                    -captionSL.getHeight());
            captionSL.draw(canvas);

            canvas.restoreToCount(restorePoint);
        }

        return canvas;
    }

}
