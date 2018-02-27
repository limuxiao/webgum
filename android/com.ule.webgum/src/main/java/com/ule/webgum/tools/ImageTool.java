package com.ule.webgum.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Title: ImageUtils       --      图片工具类
 * @Description:
 * @Author: Liy
 * @Date: 2017年04月24日 14:26
 * @Version: V1.0
 */

public class ImageTool {

    /**
    21.     * Get bitmap from specified image path
    23.     * @param imgPath
    24.     * @return
    25.     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
      * Store bitmap into specified image path
      * @param bitmap
      * @param outPath
      * @throws FileNotFoundException
      */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
    * Compress image by pixel, this will modify image width/height.
    * Used to get thumbnail
    * @param imgPath image path
    * @param pixelW target pixel of width
    * @param pixelH target pixel of height
    * @return
    */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
        //        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
    * Compress image by size, this will modify image width/height.
    * Used to get thumbnail
    * @param image
    * @param pixelW target pixel of width
    * @param pixelH target pixel of height
    * @return
    */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
        //      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
    * Compress by quality,  and generate image to the path specified
    * @param image
    * @param outPath
    * @param maxSize target will be compressed to be smaller than this size.(kb)
    * @throws IOException
    */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }


    public static Bitmap compressAndGenImage(Bitmap image, int maxSize) throws IOException {
        Bitmap bitmap = image;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        return bitmap;
    }

    /**
    * Compress by quality,  and generate image to the path specified
    * @param imgPath
    * @param outPath
    * @param maxSize target will be compressed to be smaller than this size.(kb)
    * @param needsDelete Whether delete original file after compress
    * @throws IOException
    */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
    * Ratio and generate thumb to the path specified
    * @param image
    * @param outPath
    * @param pixelW target pixel of width
    * @param pixelH target pixel of height
    * @throws FileNotFoundException
    */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage( bitmap, outPath);
    }

    /**
    * Ratio and generate thumb to the path specified
    * @param
    * @param outPath
    * @param pixelW target pixel of width
    * @param pixelH target pixel of height
    * @param needsDelete Whether delete original file after compress
    * @throws FileNotFoundException
    */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage( bitmap, outPath);
        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }


    /**
     * 从网络上获取图片     --  线程锁
     * @param path      图片地址
     * @return
     * @throws Exception
     */
    public synchronized static File downloadImage(Context context, String path) throws Exception {

        File file = new File(getDefaultPath(context));
        return saveFile(BitmapFactory.decodeStream(getImageStream(path)),file.getPath(), String.valueOf(System.currentTimeMillis()) + ".jpg");
    }


    /**
     * Get image from newwork
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get image from newwork
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }


    /**
     * Get data from stream
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 保存文件
     * @param bm
     * @throws IOException
     */
    public static File saveFile(Bitmap bm) throws IOException {
        return saveFile(bm, String.valueOf(System.currentTimeMillis()) + ".jpg");
    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        return saveFile(bm,imageStorageDir.getPath(),fileName);
    }

    public static File saveFile(Bitmap bm, String filePath, String fileName)throws IOException {
        File imageStorageDir = new File(filePath);
        if (! imageStorageDir.exists())
            imageStorageDir.mkdirs();
        File file = new File(imageStorageDir + File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();

        return file;
    }



    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        return mergeBitmap(firstBitmap,secondBitmap,0,0);
    }

    /**
     * 图片合成     --      将两幅图片合成一幅
     * @param firstBitmap   第一幅图片
     * @param secondBitmap  第二幅图片
     * @param w             第二幅图片左上角相对于第一幅图片的x轴偏移
     * @param h             第二幅图片左上角相对于第一幅图片的y轴偏移
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, w, h, null);
        return bitmap;
    }

    /**
     * 在图片上添加文字
     * @param gText             文本内容
     * @param bitmap            图片
     * @param textSize          文字大小
     * @param textColor         文字颜色
     * @param x                 文字中心点位置对应图片的x轴偏移
     * @param y                 文字中心点位置对应图片的y轴偏移
     * @return
     */
    public static Bitmap drawTextToBitmap(String gText, Bitmap bitmap, int textSize, int textColor, float x, float y){

        Bitmap.Config bitmapConfig = bitmap.getConfig();

        if(bitmapConfig == null)
            bitmapConfig = Bitmap.Config.ARGB_8888;

        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        paint.setTypeface(Typeface.DEFAULT); // 采用默认的宽度
        paint.setColor(textColor);
        paint.setTextSize(textSize);
//        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);  //设置阴影

        float w = paint.measureText(gText);     //获取文字宽度
        float X = x - w/2;
        float Y = y + textSize/2;

        canvas.drawText(gText, X, Y, paint);

        return bitmap;
    }


    public static String getDefaultPath(Context context){
        String path = "";
        //判断版本
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {        //小于19
            File docsFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Documents");
            path = docsFolder.getPath();
        } else {
            path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        }
        return path + File.separator + "Image";
    }


    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap, int compress) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, compress, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
