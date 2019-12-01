package com.example.kotlinmvpcalculator._network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VehicleServiceBuilder {

    private const val API_URL ="https://fake-poi-api.mytaxi.com";
    private var okHttpClient = OkHttpClient.Builder()

    private var builder = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())

    private var retrofit = builder.build();

    fun <T> builderService (serviceType : Class<T>): T {

        return retrofit.create(serviceType)
    }


}