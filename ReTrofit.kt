package com.example.bustract.Responses

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object ReTrofit {
 val baseurl="https://wizzie.online/TrackMyBus/"



private val client:OkHttpClient=OkHttpClient.Builder()
    .addInterceptor {
        chain->
        val request=chain.request()
        val builder=request.newBuilder()
            .method(request.method,request.body)
chain.proceed(builder.build())
    }.build()

    val instance :Api by lazy {
        val retrofit=retrofit2.Retrofit.Builder()
            .client(client)
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java)
    }

}