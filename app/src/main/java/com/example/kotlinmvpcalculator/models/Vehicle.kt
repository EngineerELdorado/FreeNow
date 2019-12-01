package com.example.kotlinmvpcalculator.models

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "VEHICLES")
class Vehicle {

     @SerializedName("id")
     @PrimaryKey
     @NonNull
     var id:Int=0
     @SerializedName("heading")
     var heading:String=""
     @SerializedName("fleetType")
     var fleetType:String=""
     @SerializedName("coordinate")
     @Embedded
     var coordinate:Coordinate?=null



    override fun toString(): String {
        return "Vehicle(id=$id, heading='$heading', fleetType='$fleetType', coordinate=$coordinate)"
    }



}