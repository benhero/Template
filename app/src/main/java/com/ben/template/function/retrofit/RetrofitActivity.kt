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
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()

        // 创建Retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://v1.alapi.cn/api/fanyi/")
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
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
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()

        // 创建Retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/")
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
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
                val body = response.body() as TranslationResponse
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
                    get(src_text.text.toString().trim())
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