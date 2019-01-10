package com.waracle.androidtest.dataSource

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.waracle.androidtest.ImageLoader
import com.waracle.androidtest.MainApplication
import com.waracle.androidtest.R
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ConcurrentLinkedQueue

class ImageSources : DataSources{

    private var byteData: ByteArray? = null
    private var startTimeStamp:Long = 0
    private var url:String = ""
    private var bitmap: Bitmap? = null
    private var imageSourceListener:ConcurrentLinkedQueue<WeakReference<DataSources.DataListeners<Any>>> = ConcurrentLinkedQueue()



    override fun getUrl(): String {
        return url
    }

    fun setUrl(url: String): ImageSources {
        this.url = url
        return this
    }

    private fun getTimeStamp(): Long {
        return startTimeStamp
    }

    override fun getDataSourceListeners(): ConcurrentLinkedQueue<WeakReference<DataSources.DataListeners<Any>>> {
        return imageSourceListener
    }

    override suspend fun run() {
        if(bitmap == null ||
                getTimeStamp() > MainApplication.getDataSource().getTimeToCache() + getTimeStamp()){
            startTimeStamp = System.currentTimeMillis()
            val byteData = loadImageData(url)
            if (byteData != null) {
                bitmap = convertToBitmap(byteData)
            }
            if (bitmap == null) {
                bitmap = ImageLoader.getBitmapFromVectorDrawable(MainApplication.getInstance(), R.drawable.ic_block_black_24dp)
            }
        }

        while (imageSourceListener.peek() != null){
            val dataListener = imageSourceListener.poll()
            if (dataListener?.get() != null){
                dataListener.get()?.onDataRetrieved(bitmap!!)
            }
        }
    }

    @Throws(IOException::class)
    private fun loadImageData(url:String):ByteArray?{
        HttpURLConnection.setFollowRedirects(true)
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        if (connection.responseCode == 301) {
            loadImageData(connection.getHeaderField("Location"))
        }else{
            try {
                byteData = connection.inputStream.readBytes()
                connection.inputStream.close()
            }catch (e:IOException){
            }finally{
                connection.disconnect()
            }
        }
        return byteData
    }

    private fun convertToBitmap(data: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }
}