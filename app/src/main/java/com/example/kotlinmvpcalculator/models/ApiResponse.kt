package com.example.kotlinmvpcalculator.models

import com.google.gson.annotations.SerializedName

class ApiResponse {
    @SerializedName("poiList")
   var vehicles:List<Vehicle>?=null
}