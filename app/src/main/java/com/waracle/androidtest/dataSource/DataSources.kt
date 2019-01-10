package com.waracle.androidtest.dataSource

import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentLinkedQueue

interface DataSources{
    fun getUrl():String
    fun getDataSourceListeners(): ConcurrentLinkedQueue<WeakReference<DataListeners<Any>>>
    suspend fun run()

    interface DataListeners<R>{
        suspend fun onDataRetrieved(result: R)
    }
}