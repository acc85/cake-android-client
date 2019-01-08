package com.waracle.androidtest.DataSource;

import com.waracle.androidtest.Model.MainModel;
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
import java.util.Iterator;
import java.util.List;

public class CakeDataSources implements DataSources<List<MainModel>> {

    List<MainModel> mainModels = new ArrayList<>();

    List<WeakReference<DataListeners<List<MainModel>>>> dataListeners = new ArrayList<>() ;

    @Override
    public String getUrl() {
        return "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    }

    @Override
    public List<WeakReference<DataListeners<List<MainModel>>>> getDataSourceListeners() {
        return dataListeners;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        HttpURLConnection.setFollowRedirects(true);
        HttpURLConnection connection = null;
        byte[] byteData = null;
        try {
            connection = (HttpURLConnection) new URL(getUrl()).openConnection();
                try {
                    // Read data from workstation
                    inputStream = connection.getInputStream();
                } catch (IOException e) {
                    // Read the error from the workstation
                    inputStream = connection.getErrorStream();
                }
                byteData = StreamUtils.readUnknownFully(inputStream);
                String jsonText = new String(byteData, "UTF-8");
                JSONArray jsonArray = new JSONArray(jsonText);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonElement = jsonArray.getJSONObject(i);

                    MainModel mainModel = new MainModel();
                    mainModel.setTitle(jsonElement.getString("title"));
                    mainModel.setDesc(jsonElement.getString("desc"));
                    mainModel.setImageUrl(jsonElement.getString("image"));
                    mainModels.add(mainModel);
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

        for (Iterator<WeakReference<DataListeners<List<MainModel>>>> it = dataListeners.iterator(); it.hasNext(); ) {
            it.next().get().onDataRetrieved(mainModels);
            it.remove();
        }
    }
}
