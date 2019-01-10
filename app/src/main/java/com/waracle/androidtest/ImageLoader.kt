package com.waracle.androidtest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.waracle.androidtest.dataSource.DataSources
import com.waracle.androidtest.dataSource.ImageSources
import java.security.InvalidParameterException

class ImageLoader{

    lateinit var dataListener: DataSources.DataListeners<Any>

    fun load(url: String, imageView: ImageView) {

        if (TextUtils.isEmpty(url)) {
            throw InvalidParameterException("URL is empty!")
        }

        dataListener = object : DataSources.DataListeners<Any> {
            override suspend fun onDataRetrieved(result: Any) {
                Handler(Looper.getMainLooper()).post {
                    setImageView(imageView, result as Bitmap)
                    imageView.tag = null
                }
            }
        }

        MainApplication.getDataSource().addToMap(url, ImageSources().setUrl(url), dataListener)
    }

    companion object {
        fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
            val drawable = ContextCompat.getDrawable(context, drawableId)
            var bitmap: Bitmap? = null
            if (drawable != null) {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap!!)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }

            return bitmap
        }
    }


    private fun setImageView(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}