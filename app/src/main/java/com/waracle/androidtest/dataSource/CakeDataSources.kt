package com.waracle.androidtest.dataSource

import com.waracle.androidtest.model.CakeModel
import org.json.JSONArray
import java.lang.System.currentTimeMillis
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ConcurrentLinkedQueue

class CakeDataSources : DataSources {
    private var startTimeStamp: Long = 0

    private var cakeModels: MutableList<CakeModel> = mutableListOf()

    private var dataListeners: ConcurrentLinkedQueue<WeakReference<DataSources.DataListeners<Any>>> = ConcurrentLinkedQueue()

    override fun getUrl(): String {
        return "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"
    }

    override fun getDataSourceListeners(): ConcurrentLinkedQueue<WeakReference<DataSources.DataListeners<Any>>> {
        return dataListeners
    }

    override suspend fun run() {
        if (cakeModels.isEmpty() || startTimeStamp > currentTimeMillis() + startTimeStamp) {
            startTimeStamp = currentTimeMillis()
            HttpURLConnection.setFollowRedirects(true)
            val connection = URL(getUrl()).openConnection() as HttpURLConnection
            connection.connect()
            val charset: String? = parseCharset(connection.getRequestProperty("Content-Type"))
            try {
                connection.inputStream.bufferedReader(
                        charset(charset ?: "UTF-8")
                ).use { reader ->
                    val jsonText: String = reader.readText()
                    val jsonArray = JSONArray(jsonText)
                    (0..(jsonArray.length() - 1)).forEach {
                        val jsonElement = jsonArray.getJSONObject(it)
                        val cakeModel = CakeModel(
                                jsonElement.getString("title"),
                                jsonElement.getString("desc"),
                                jsonElement.getString("image"))
                        cakeModels.add(cakeModel)
                    }
                }
            } finally {
                connection.disconnect()

            }
        }

        while (dataListeners.peek() != null) {
            val dataListener: WeakReference<DataSources.DataListeners<Any>> = dataListeners.poll()
            if (dataListener.get() != null) {
                dataListener.get()?.onDataRetrieved(cakeModels)
            }

        }
    }

    private fun parseCharset(contentType: String?): String {
        val params: Array<String> = emptyArray()
        contentType?.split(",")?.forEachIndexed { index, value ->
            params[index] = value
        }
        for (i in 1 until params.size) {
            for (string in params) {
                val pair = params[i].trim().split("=".toRegex())
                if (pair.size == 2) {
                    if (pair[0] == "charset") {
                        return pair[1]
                    }
                }
            }
        }
        return "UTF-8"
    }


}