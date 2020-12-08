package com.ben.template.function.retrofit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import com.elvishew.xlog.XLog
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        translate_btn.setOnClickListener(this)
    }

    private fun request(src: String) {
        val sslParams = HttpsUtil.getSslSocketFactory(null, null, null)
        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()

        //步骤4:创建Retrofit对象
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://v1.alapi.cn/api/fanyi/") // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .client(client)
            .build()

        // 步骤5:创建 网络请求接口 的实例
        val request: GetRequest_Interface = retrofit.create(GetRequest_Interface::class.java)

        //对 发送请求 进行封装
        val call: Call<Translation> = request.getCall(src)

        //步骤6:发送网络请求(异步)
        call.enqueue(object : Callback<Translation?> {
            //请求成功时候的回调
            override fun onResponse(call: Call<Translation?>?, response: Response<Translation?>) {
                //请求处理,输出结果
                val body = response.body()
                XLog.i(body.toString())
                if (body is Translation) {
                    var a = ""
                    body.data.trans_result.forEach {
                        a += it.dst
                    }
                    dst_text.setText(a)
                }
            }

            //请求失败时候的回调
            override fun onFailure(call: Call<Translation?>?, throwable: Throwable?) {
                XLog.e("连接失败 : " + throwable?.message)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            translate_btn -> {
                request(src_text.text.toString().trim())
            }
            else -> {
            }
        }
    }
}