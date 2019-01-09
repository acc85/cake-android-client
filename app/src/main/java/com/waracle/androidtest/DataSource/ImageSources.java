package com.waracle.androidtest.DataSource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.MainApplication;
import com.waracle.androidtest.R;
import com.waracle.androidtest.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageSources implements DataSources<Bitmap> {

    private boolean running;
    private long startTimeStamp;
    private String url;
    private byte[] byteData;
    private Bitmap bitmap;
    private List<WeakReference<DataListeners<Bitmap>>> imageSourceListener = new ArrayList<>();


    public String getUrl() {
        return url;
    }

    @Override
    public List<WeakReference<DataListeners<Bitmap>>> getDataSourceListeners() {
        return imageSourceListener;
    }

    public ImageSources setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public long getTimeStamp() {
        return startTimeStamp;
    }

    @Override
    public void run() {
        if (bitmap == null ||
                getTimeStamp() > MainApplication.getDataSource().getTimeToCache() + getTimeStamp()) {
            startTimeStamp = System.currentTimeMillis();
            byte[] bytedata = new byte[0];
            try {
                bytedata = loadImageData(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bytedata != null) {
                bitmap = convertToBitmap(bytedata);
            }
            if (bitmap == null) {
                bitmap = ImageLoader.getBitmapFromVectorDrawable(MainApplication.getInstance(), R.drawable.ic_block_black_24dp);
            }
        }

        Iterator<WeakReference<DataListeners<Bitmap>>> it = imageSourceListener.iterator();
        while (it.hasNext()) {
            WeakReference<DataListeners<Bitmap>> dataListeners = it.next();
            if (dataListeners.get() != null) {
                dataListeners.get().onDataRetrieved(bitmap);
            }
            it.remove();
        }
    }

    private byte[] loadImageData(String url) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection.setFollowRedirects(true);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            if (connection.getResponseCode() == 301) {
                loadImageData(connection.getHeaderField("Location"));
            } else {
                try {
                    // Read data from workstation
                    inputStream = connection.getInputStream();
                } catch (IOException e) {
                    // Read the error from the workstation
                    inputStream = connection.getErrorStream();
                }
                byteData = StreamUtils.readUnknownFully(inputStream);
                // Can you think of a way to make the entire
                // HTTP more efficient using HTTP headers??=
            }
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            if (connection != null) {
                connection.disconnect();
            }
        }
        return byteData;
    }

    private static Bitmap convertToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
