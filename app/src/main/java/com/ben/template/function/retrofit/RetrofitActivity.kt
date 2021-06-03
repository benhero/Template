package com.ben.template.function.retrofit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ben.template.R
import com.ben.template.databinding.ActivityRetrofitBinding
import com.elvishew.xlog.XLog
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset


/**
 * Retrofit样例
 * @see <a href="https://blog.csdn.net/carson_ho/article/details/73732076">参考文章 - CSDN</a>
 * @see <a href="https://segmentfault.com/a/1190000005638577">参考文章 - Bugly</a>
 * @author Benhero
 */
class RetrofitActivity : AppCompatActivity(), View.OnClickListener {
    /**
     * Get请求
     * https://www.free-api.com/doc/371
     */
    private val alRequest: RequestApi = initAlRequest()
    private fun initAlRequest(): RequestApi {
        val sslParams = SSLUtil.getSslSocketFactory(null, null, null)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptor())
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()

        // 创建Retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://v1.alapi.cn/api/fanyi/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(RequestApi::class.java)
    }

    /**
     * 有道post请求
     */
    private val youdaoRequest: RequestApi = initYoudaoRequest()
    private fun initYoudaoRequest(): RequestApi {

        val sslParams = SSLUtil.getSslSocketFactory(null, null, null)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptor())
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()

        // 创建Retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(RequestApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityRetrofitBinding>(this, R.layout.activity_retrofit)

        translate_btn.setOnClickListener(this)
    }

    private fun get(src: String) {
        //对 发送请求 进行封装
        val call: Call<TranslationResponse> = alRequest.getCall(src)

        //发送网络请求(异步)
        call.enqueue(object : Callback<TranslationResponse> {
            //请求成功时候的回调
            override fun onResponse(
                call: Call<TranslationResponse>,
                response: Response<TranslationResponse>
            ) {
                //请求处理,输出结果
                val body: TranslationResponse = response.body() ?: return
                XLog.i(body.toString())
                var a = ""
                body.data.trans_result.forEach {
                    a += it.dst
                }
                get_text.text = a
            }

            //请求失败时候的回调
            override fun onFailure(call: Call<TranslationResponse>, throwable: Throwable?) {
                XLog.e("连接失败 : " + throwable?.message)
            }
        })
    }

    companion object {
        const val TAG = "JKL"
        val UTF8: Charset = Charset.forName("UTF-8")
    }

    class MyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response? {
            val request = chain.request()
            val oldHeaders = request.headers()
            val tag = RequestApi.ApiTag.tag[oldHeaders.get(RequestApi.ApiTag.KEY)]
            XLog.d("[$tag]request start")


            val requestBuilder = request.newBuilder()

            //添加固定header
            requestBuilder.headers(oldHeaders)
//                .addHeader("sdkVer", "0")
                .removeHeader("Tag")

            //.body
            val oldBody = request.body()
            if (oldBody != null) {
                requestBuilder.post(oldBody)
            }
            //链接
            requestBuilder.url(request.url())

            val newRequest: Request
            var response: okhttp3.Response? = null
//            val ts = (System.currentTimeMillis() / 1000).toString() //用来md5计算和拼接在url后面,不放在请求body里面
            val newHttpUrl = request.url().newBuilder()
//                .addQueryParameter("ts", ts)
                .build()
            newRequest = requestBuilder.url(newHttpUrl).build()
            try {
                response = chain.proceed(newRequest)
            } catch (e: Exception) {
                XLog.e(e)
            }

            if (response == null) {
                XLog.e("[$tag]request error")
                return null
            }
            XLog.d("[$tag]url:${response.request().url()}")
            //打印请求数据信息：header
            val headers = newRequest.headers()
            for (name in headers.names()) {
                XLog.d("[$tag]header:$name:${headers[name]}")
            }
            //request body:
            val method = newRequest.method()
            if ("POST" == method) {
                if (newRequest.body() is FormBody) {
                    val sb = StringBuilder()
                    val body = newRequest.body() as FormBody
                    for (i in 0 until body.size()) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                    }

                    sb.delete(sb.length - 1, sb.length)
                    XLog.d("[$tag]request body:$sb")
                } else {
                    newRequest.body()?.let {
                        val buffer = Buffer()
                        it.writeTo(buffer)
                        val contentType = it.contentType()
                        if (contentType != null) {
                            val charset = contentType.charset(UTF8)
                            if (charset != null) {
                                val bodyStr = buffer.readString(charset)
                                XLog.d("[$tag]contentType:$contentType\tbody:$bodyStr")
                            }
                        }
                    }
                }
            }
            //response
            val responseBody = response.peekBody(1024 * 1024)
            XLog.d("[$tag]response body:${responseBody.string()}")
            return response
        }
    }

    private fun post(src: String) {

        //对 发送请求 进行封装
        val call: Call<YoudaoResponse> = youdaoRequest.postCall(src)

        //发送网络请求(异步)
        call.enqueue(object : Callback<YoudaoResponse> {
            //请求成功时候的回调
            override fun onResponse(
                call: Call<YoudaoResponse>?,
                response: Response<YoudaoResponse>
            ) {
                //请求处理,输出结果
                val body = response.body() as YoudaoResponse
                post_text.text = body.translateResult[0][0].tgt
                XLog.i(body.toString())
            }

            //请求失败时候的回调
            override fun onFailure(call: Call<YoudaoResponse>, throwable: Throwable?) {
                XLog.e("连接失败 : " + throwable?.message)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            translate_btn -> {
                try {
//                    get(src_text.text.toString().trim())
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "网络访问错误", Toast.LENGTH_SHORT).show()
                }

                try {
                    post(src_text.text.toString().trim())
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "网络访问错误", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }
    }
}