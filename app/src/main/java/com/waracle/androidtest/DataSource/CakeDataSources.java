package com.waracle.androidtest.DataSource;

import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.StreamUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CakeDataSources implements DataSources<List<CakeModel>> {

    private long startTimeStamp = 0L;

    private List<CakeModel> cakeModels = new ArrayList<>();

    private ConcurrentLinkedQueue<WeakReference<DataListeners<List<CakeModel>>>> dataListeners = new ConcurrentLinkedQueue<>();

    @Override
    public String getUrl() {
        return "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    }

    @Override
    public ConcurrentLinkedQueue<WeakReference<DataListeners<List<CakeModel>>>> getDataSourceListeners() {
        return dataListeners;
    }

    @Override
    public void run() {
        if (cakeModels.isEmpty() || startTimeStamp > System.currentTimeMillis() + startTimeStamp) {
            startTimeStamp = System.currentTimeMillis();
            InputStream inputStream = null;
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(getUrl()).openConnection();
                try {
                    // Read data from workstation
                    inputStream = connection.getInputStream();
                } catch (IOException e) {
                    // Read the error from the workstation
                    inputStream = connection.getErrorStream();
                }
                byte[] bytes = StreamUtils.readUnknownFully(inputStream);
                // Read in charset of HTTP content.
                String charset = parseCharset(connection.getRequestProperty("Content-Type"));

                // Convert byte array to appropriate encoded string.
                String jsonText = new String(bytes, charset);
                JSONArray jsonArray = new JSONArray(jsonText);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonElement = jsonArray.getJSONObject(i);
                    CakeModel cakeModel = new CakeModel();
                    cakeModel.setTitle(jsonElement.getString("title"));
                    cakeModel.setDesc(jsonElement.getString("desc"));
                    cakeModel.setImageUrl(jsonElement.getString("image"));
                    cakeModels.add(cakeModel);
                }
                // Can you think of a way to make the entire
                // HTTP more efficient using HTTP headers??=
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                // Close the input stream if it exists.
                StreamUtils.close(inputStream);
                // Disconnect the connection
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        while (dataListeners.peek() != null) {
            WeakReference<DataListeners<List<CakeModel>>> dataListener = dataListeners.poll();
            if (dataListener != null && dataListener.get() != null) {
                dataListener.get().onDataRetrieved(cakeModels);
            }

        }

    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    private String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }
}
