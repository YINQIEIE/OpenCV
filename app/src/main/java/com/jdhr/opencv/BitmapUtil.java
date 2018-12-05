package com.jdhr.opencv;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/10/25.
 */

public class BitmapUtil {

    public static final String TEMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static Bitmap getBitmap(View view) {
        view.buildDrawingCache(true);
        view.buildDrawingCache();
        view.setDrawingCacheEnabled(false);
        return view.getDrawingCache();
    }

    /**
     * 默认保存为格式 jpg
     *
     * @param bm       bm
     * @param fileName 文件名
     * @return
     */
    public static Uri saveBitmap(Bitmap bm, String fileName) {
        return saveBitmap(bm, fileName, 0);
    }

    /**
     * 保存图片
     *
     * @param bm       bitmap
     * @param fileName 文件名称
     * @param picType  图片类型 {@link android.graphics.Bitmap.CompressFormat}
     * @return
     */
    public static Uri saveBitmap(Bitmap bm, String fileName, int picType) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(TEMP_DIR);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath() + File.separator + fileName + getPicSuffix(picType));
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(getCompressFormat(picType), 100, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String saveBitmap(Bitmap bm) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(TEMP_DIR, "OpenCV");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        String fileName = "FACE_" + UUID.randomUUID().toString() + System.currentTimeMillis() + ".jpg";
        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath(), fileName);
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return img.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据文件类型获取保存后缀名
     *
     * @param picType
     * @return
     */
    public static String getPicSuffix(int picType) {
        switch (picType) {
            case 0:
            default:
                return ".jpg";
            case 1:
                return ".png";
            case 2:
                return ".webp";
        }
    }

    /**
     * 根据类型获取压缩格式
     *
     * @param picType
     * @return
     */
    public static Bitmap.CompressFormat getCompressFormat(int picType) {
        switch (picType) {
            case 0:
            default:
                return Bitmap.CompressFormat.JPEG;
            case 1:
                return Bitmap.CompressFormat.PNG;
            case 2:
                return Bitmap.CompressFormat.WEBP;
        }
    }

    /**
     * 获取圆角矩形或圆形图片的方法
     *
     * @param bitmap 待处理 bitmap 对象
     * @param pixels 圆角半径，x 和 y 相等
     * @return 返回圆角或者圆形 bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        //柱：一定要先画圆角矩形
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
