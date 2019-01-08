package com.waracle.androidtest.Storage;

import android.graphics.Bitmap;
import androidx.collection.LruCache;


public class ImageCache {
    private LruCache<String, Bitmap> cakeImages;

    private static ImageCache cache;

    public static ImageCache getInstance() {
        if (cache == null) {
            cache = new ImageCache();
        }
        return cache;
    }

    public void initializeCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 8;

        System.out.println("cache size = " + cacheSize);

        cakeImages = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap value) {

                int bitmapByteCount = value.getRowBytes() * value.getHeight();

                return bitmapByteCount / 1024;
            }
        };
    }

    public void addImageToWarehouse(String key, Bitmap value) {
        if (cakeImages != null && cakeImages.get(key) == null && value != null) {
            cakeImages.put(key, value);
        }
    }

    public Bitmap getImageFromWarehouse(String key) {
        if (key != null) {
            return cakeImages.get(key);
        } else {
            return null;
        }
    }

    public void removeImageFromWarehouse(String key) {
        cakeImages.remove(key);
    }

    public void clearCache() {
        if (cakeImages != null) {
            cakeImages.evictAll();
        }
    }

}
