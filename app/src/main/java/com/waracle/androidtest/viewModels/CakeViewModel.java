package com.waracle.androidtest.viewModels;

import android.os.Handler;
import android.os.Looper;

import com.waracle.androidtest.DataSource.CakeDataSources;
import com.waracle.androidtest.DataSource.DataSources;
import com.waracle.androidtest.MainApplication;
import com.waracle.androidtest.Model.CakeModel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CakeViewModel extends ViewModel {

    @SuppressWarnings("FieldCanBeLocal")
    private DataSources.DataListeners<List<CakeModel>> dataListener;

    private MutableLiveData<List<CakeModel>> cakeModels = new MutableLiveData<>();

    public MutableLiveData<List<CakeModel>> getCakeModels(){
        return cakeModels;
    }

    public void fetchCakeModels(){
        dataListener = new DataSources.DataListeners<List<CakeModel>>() {
            @Override
            public void onDataRetrieved(final List<CakeModel> result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cakeModels.postValue(result);
                    }
                });
            }
        };

        String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        MainApplication.getDataSource().addToMap(JSON_URL, new CakeDataSources(), dataListener);
    }

    public CakeModel getCakeModelByIndex(int index){
        return cakeModels.getValue().get(index);
    }

}
