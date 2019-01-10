package com.waracle.androidtest.dataSource

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

class DataSourceManager{

    private val timeToCache = 1000000L
    private val dataSourceConcurrentMap = ConcurrentHashMap<String, DataSources>()

    fun getTimeToCache(): Long {
        return timeToCache
    }

    fun addToMap(url: String, dataSource: DataSources, imageSourceListener: DataSources.DataListeners<Any>) {
        val storedDataSource = dataSourceConcurrentMap[url]
        var dataS:DataSources = dataSource
        if (storedDataSource == null) {
            dataSourceConcurrentMap[url] = dataSource
        } else {
            dataS = storedDataSource
        }
        dataS.getDataSourceListeners().add(WeakReference(imageSourceListener))
        val finalDataSource = dataS
        GlobalScope.launch {
            finalDataSource.run()
        }
    }
}