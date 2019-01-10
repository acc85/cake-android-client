package com.waracle.androidtest

import android.app.Application
import com.waracle.androidtest.dataSource.DataSourceManager

class MainApplication : Application(){


    companion object {
        lateinit var application:MainApplication
        private val dataSourceManager: DataSourceManager = DataSourceManager()


        fun getInstance():MainApplication{
            return application
        }

        fun getDataSource():DataSourceManager{
            return dataSourceManager
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}