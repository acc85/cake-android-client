package com.waracle.androidtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.waracle.androidtest.DataSource.DataSources;
import com.waracle.androidtest.DataSource.ImageSources;

import java.security.InvalidParameterException;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader {

    public ImageLoader() { /**/ }

    @SuppressWarnings("FieldCanBeLocal")
    private DataSources.DataListeners dataListener;

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     * @param imageView view to set image too.
     */
    public void load(final String url, final ImageView imageView) {

        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        dataListener = new DataSources.DataListeners<Bitmap>() {
            @Override
            public void onDataRetrieved(final Bitmap bitmap) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        setImageView(imageView, bitmap);
                        imageView.setTag(null);
                    }
                });
            }
        };
        MainApplication.getDataSource().addToMap(url, new ImageSources().setUrl(url), dataListener);

    }

    @Nullable
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = null;
        if (drawable != null) {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return bitmap;
    }


    private void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
