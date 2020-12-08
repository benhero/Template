package com.ben.template.function.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * com.ben.template.function.retrofit
 *
 * @author chenbenbin
 * @date   12/8/20
 */
interface GetRequest_Interface {

//    @GET("?q=hello&from=en&to=zh")
//    fun getCall(): Call<Translation>

    @GET("/api/fanyi")
    fun getCall(
        @Query("q") word: String = "hello",
        @Query("from") from: String = "zh",
        @Query("to") to: String = "en"
    ): Call<Translation>
//
//    @GET("users/{user}/repos")
//    fun listRepos(@Path("user") user: String?): Call<List<Repo?>?>?
}