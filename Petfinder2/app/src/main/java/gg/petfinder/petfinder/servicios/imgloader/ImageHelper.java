package gg.petfinder.petfinder.servicios.imgloader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageHelper {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap cropBitmapToSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width) ? height - (height - width)
                : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0) ? 0 : cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0) ? 0 : cropH;

        return Bitmap.createBitmap(
                bitmap, cropW, cropH, newWidth, newHeight);
    }





    public static Bitmap FormatearImagen(File pictureFile,byte[] data, Activity activity)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 30;

        options.inJustDecodeBounds = false;


        Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        bMap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


        Display d =  activity.getWindowManager().getDefaultDisplay();
        int x = d.getWidth();
        int y = d.getHeight();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bMap, 300, 300, true);
        Matrix matrix = new Matrix();
        matrix.postRotate(90); // anti-clockwise by 90 degrees

        // create a new bitmap from the original using the matrix to transform the result
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap.getWidth(), scaledBitmap .getHeight(), matrix, true);

    return rotatedBitmap;
    }
}