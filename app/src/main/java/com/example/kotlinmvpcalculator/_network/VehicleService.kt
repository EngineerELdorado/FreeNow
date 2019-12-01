package com.example.kotlinmvpcalculator._network

import com.example.kotlinmvpcalculator.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface VehicleService {

    @GET("/?p1Lat=53.694865&p1Lon=9.757589&p2Lat=53.394655&p2Lon=10.099891")
    fun getAll():Call<ApiResponse>
}