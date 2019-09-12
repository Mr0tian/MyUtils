package com.example.daotest.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 小米......
 */


public class PictureUtil {
    private static final String TAG = "PictureUtil";
    private static final String IMAGE_FILE_NAME = "workorder.jpg";
    private static final String MyPetRootDirectory = Environment.getExternalStorageDirectory() + File.separator + "workorder";

    public static String getWorkorderRootDirectory() {
        return MyPetRootDirectory;
    }

    public static boolean createPicDir() {
        try {
            File file = new File(PictureUtil.getWorkorderRootDirectory());
            if (!file.exists()) {
                file.mkdir();
            }
            file = new File(PictureUtil.getWorkorderRootDirectory() + "/image");
            if (!file.exists()) {
                file.mkdir();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Uri getImageUri(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(context, contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(context, uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
        } else {
            uri = data.getData();
            imagePath = getImagePath(context, uri, null);
        }
        File file = new File(imagePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,
                    "com.zhongrui.myworkorder.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static long getLastModified(Context context, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(context, contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(context, uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
        } else {
            uri = data.getData();
            imagePath = getImagePath(context, uri, null);
        }
        File file = new File(imagePath);
        return file.lastModified();
    }

    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static void mkdirMyPetRootDirectory() {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sdcard是否存在
        if (isSdCardExist) {
            File MyPetRoot = new File(getWorkorderRootDirectory());
            if (!MyPetRoot.exists()) {
                try {
                    MyPetRoot.mkdir();
                    Log.d(TAG, "mkdir success");
                } catch (Exception e) {
                    Log.e(TAG, "exception->" + e.toString());
                }
            }
        }
    }

    public static void saveWatermarkImage(Context context, boolean isCapture, Intent data, String address, String id , boolean isAddWater){
        Log.e(TAG, "saveWatermarkImages address: "+address);
        if (address == null){
            address = "";
        }
        File newDir = new File(PictureUtil.getWorkorderRootDirectory() + "/image/" +id+"/");
        if(!newDir.exists()){
            newDir.mkdir();
        }
        if(isCapture){
            File pictureFile = new File(PictureUtil.getWorkorderRootDirectory(), IMAGE_FILE_NAME);
            Uri pictureUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                pictureUri = FileProvider.getUriForFile(context,
                        "com.zhongrui.myworkorder.fileprovider", pictureFile);
                Log.e(TAG, "picURI=" + pictureUri.toString());
            } else {
                pictureUri = Uri.fromFile(pictureFile);
            }
            try {
                Bitmap photo = null;
                photo = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(pictureUri));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
                String timeStr = formatter.format(new Date());
                SimpleDateFormat formatterWater = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String timeWater = formatterWater.format(new Date());
                Log.e(TAG, "REQUEST_IMAGE_CAPTURE:" + timeStr);
                File newfile = new File(PictureUtil.getWorkorderRootDirectory() + "/image/"+id, timeStr + ".jpg");
                if (!newfile.exists())
                    newfile.createNewFile();
                Bitmap saveBitmap = PictureUtil.createWatermark(context, photo, timeWater+"\n"+address, isAddWater);
                PictureUtil.save(saveBitmap, newfile, Bitmap.CompressFormat.JPEG, true);
                Toast.makeText(context, "已保存至："+newfile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                Uri uri = getImageUri(context, data);
                Bitmap photo = null;
                photo = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                long time = PictureUtil.getLastModified(context, data);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
                String timeStr = formatter.format(time);
                SimpleDateFormat formatterWater = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String timeWater = formatterWater.format(time);
                Log.e(TAG, "REQUEST_IMAGE_GET:" + timeStr);
                File newfile = new File(PictureUtil.getWorkorderRootDirectory() + "/image/"+id, timeStr + ".jpg");
                if (!newfile.exists())
                    newfile.createNewFile();
                Bitmap saveBitmap = PictureUtil.createWatermark(context, photo, timeWater+"\n"+address, isAddWater);
                //Bitmap saveBitmap = PictureUtil.createWatermark(context, photo, timeWater+address);
                PictureUtil.save(saveBitmap, newfile, Bitmap.CompressFormat.JPEG, true);
                Toast.makeText(context, "已保存至："+newfile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> saveWatermarkImages(Context context, ArrayList<String> paths, String address, String id, boolean isAddWater){
        Log.e(TAG, "saveWatermarkImages address: "+address);
        File newDir = new File(PictureUtil.getWorkorderRootDirectory() + "/image/" +id+"/");
        if(!newDir.exists()){
            newDir.mkdir();
        }
        try {
            ArrayList<String> newPaths = new ArrayList<String>();
            for(int i=0;i<paths.size();i++){
                //Bitmap photo = BitmapFactory.decodeFile(paths.get(i));
                Bitmap photo = getCompressImageFromPath(paths.get(i));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
                File file = new File(paths.get(i));
                String timeStr = formatter.format(file.lastModified());
                SimpleDateFormat formatterWater = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                String timeWater = formatterWater.format(file.lastModified());
                Log.e(TAG, "REQUEST_IMAGE_CAPTURE:" + timeStr);
                String newPath = PictureUtil.getWorkorderRootDirectory() + "/image/"+id+"/"+timeStr + ".jpg";
                File newfile = new File(newPath);
                if (!newfile.exists())
                    newfile.createNewFile();
                Bitmap saveBitmap = PictureUtil.createWatermark(context, photo, timeWater+"\n"+address, isAddWater);
                //Bitmap saveBitmap = PictureUtil.createWatermark(context, photo, timeWater+address);
                PictureUtil.save(saveBitmap, newfile, Bitmap.CompressFormat.JPEG, true);
                newPaths.add(newPath);
            }
            return newPaths;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存图片到文件File。
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true 成功 false 失败
     */
    public static boolean save(Bitmap src, File file, Bitmap.CompressFormat format, boolean recycle) {
        if (isEmptyBitmap(src))
            return false;
        OutputStream os;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, 80, os);
            if (recycle && !src.isRecycled())
                src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Bitmap对象是否为空。
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }


    public static Bitmap createWatermark(Context context, Bitmap bitmap, String markText, boolean isAddWater) {
        Log.e(TAG, "createWatermark markText: "+markText);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (isAddWater && !TextUtils.isEmpty(markText)) {
            int screenWidth = getScreenWidth(context);
            float textSize = dp2px(context, 15) * bitmapWidth / screenWidth;
            TextPaint mPaint = new TextPaint();
            Rect textBounds = new Rect();
            mPaint.setTextSize(textSize);
            mPaint.setShadowLayer(0.5f, 0f, 1f, Color.BLACK);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.getTextBounds(markText, 0, markText.length(), textBounds);
            mPaint.setColor(Color.WHITE);
            StaticLayout layout = new StaticLayout(markText, 0, markText.length(), mPaint, (int) (bitmapWidth - textSize),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.5F, true);
            float textX = 0;//dp2px(context, 8) * bitmapWidth / screenWidth;
            //float textY = bitmapHeight / 2;//图片的中间
            float textY = 0;//bitmapHeight-dp2px(context, 8) *20;
            canvas.translate(textX, textY);
            layout.draw(canvas);

        }
        //canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();
        return bmp;
    }

    private static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static String getPictureName(String path){
        int index = path.lastIndexOf("/")+1;
        return  path.substring(index);
    }


    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 30;
        while ( baos.toByteArray().length / 1024>500) {	//循环判断如果压缩后图片是否大于500kb,大于继续压缩
            Log.e(TAG, "compressImage: "+(baos.toByteArray().length/1024) );
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap getCompressImageFromPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 2048f;//这里设置高度为800f
        float ww = 2048f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private static Bitmap getCompressImageFromBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 2048f;//这里设置高度为800f
        float ww = 2048f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
}
