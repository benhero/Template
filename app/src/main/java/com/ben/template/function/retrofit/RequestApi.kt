package com.ben.template.function.retrofit

import retrofit2.Call
import retrofit2.http.*

/**
 * 请求接口
 *
 * @author Benhero
 * @date   12/8/20
 */
interface RequestApi {

    /**
     * 固定参数链接
     */
    @GET("?q=hello&from=en&to=zh")
    @Headers("${ApiTag.KEY}: fanyi1")
    fun getCall(): Call<TranslationResponse>

    /**
     * 动态参数Get请求
     */
    @GET("/api/fanyi")
    @Headers("${ApiTag.KEY}: fanyi2")
    fun getCall(
        @Query("q") word: String = "hello",
        @Query("from") from: String = "zh",
        @Query("to") to: String = "en"
    ): Call<TranslationResponse>

//    /**
//     * 动态参数Post请求
//     */
//    @POST("/api/fanyi")
//    fun postCall(
//        @Query("q") word: String = "hello",
//        @Query("from") from: String = "zh",
//        @Query("to") to: String = "en"
//    ): Call<TranslationResponse>

    @POST("translate?doctype=json")
    @Headers("${ApiTag.KEY}: youdao")
    @FormUrlEncoded
    fun postCall(@Field("i") targetSentence: String?): Call<YoudaoResponse>

    object ApiTag {
        const val KEY = "tag"
        val tag = HashMap<String, String>()

        init {
            tag["fanyi1"] = "Get请求 - 固定参数"
            tag["fanyi2"] = "Get请求 - 动态参数"
            tag["youdao"] = "Post请求"
        }
    }
}