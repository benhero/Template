package com.ben.template.function.coroutine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class CoroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        loadImg()

        CoroutineScope(Dispatchers.IO).launch {
            Log.i("JKL", "Thread Name 1: " + Thread.currentThread().name)
            withContext(Dispatchers.Main) {
                Log.i("JKL", "Thread Name 2: " + Thread.currentThread().name)
            }
        }
    }

    private fun loadImg() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.i("JKL", "onCreate: " + Thread.currentThread().name)
            val bitmap =
                loadImage("https://images.unsplash.com/photo-1603139875501-8c965c19e10d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=701&q=80")
            imageView.setImageBitmap(bitmap)
        }
    }

    private suspend fun loadImage(imageUrl: String) = withContext(Dispatchers.IO) {
        Log.i("JKL", "test: " + Thread.currentThread().name)
        val url = URL(imageUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.connect()
        val inputStream = httpURLConnection.inputStream
        return@withContext BitmapFactory.decodeStream(inputStream)
    }
}