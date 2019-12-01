package com.example.kotlinmvpcalculator.models

import com.google.gson.annotations.SerializedName

class Coordinate{

    @SerializedName("latitude")
    var latitude:Double=0.0
    @SerializedName("longitude")
    var longitude:Double=0.0

    override fun toString(): String {
        return "Coordinate(latitude=$latitude, longitude=$longitude)"
    }


}