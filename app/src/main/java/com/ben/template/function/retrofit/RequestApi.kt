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
    fun getCall(): Call<TranslationResponse>

    /**
     * 动态参数Get请求
     */
    @GET("/api/fanyi")
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
    @FormUrlEncoded
    fun postCall(@Field("i") targetSentence: String?): Call<YoudaoResponse>
}