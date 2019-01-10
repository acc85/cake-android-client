package com.waracle.androidtest.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waracle.androidtest.dataSource.CakeDataSources
import com.waracle.androidtest.dataSource.DataSources.DataListeners
import com.waracle.androidtest.MainApplication
import com.waracle.androidtest.model.CakeModel

class CakeViewModel : ViewModel() {

    private lateinit var dataListeners: DataListeners<Any>

    val cakeModels: MutableLiveData<MutableList<CakeModel>> = MutableLiveData()

    @Suppress("UNCHECKED_CAST")
    fun fetchCakeModels() {
        dataListeners = object : DataListeners<Any> {
            override suspend fun onDataRetrieved(result: Any) {
                Handler(Looper.getMainLooper()).post {
                    cakeModels.postValue(result as MutableList<CakeModel>)
                }
            }
        }
        val jsonUrl = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"
        MainApplication.getDataSource().addToMap(jsonUrl, CakeDataSources(), this.dataListeners)
    }

    fun getCakeModelByIndex(index:Int):CakeModel{
        return cakeModels.value!![index]
    }
}
