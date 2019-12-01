package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinmvpcalculator.models.Vehicle

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVehicle(vehicle: Vehicle)
    @Query("SELECT * FROM VEHICLES")
    fun getAll():LiveData<List<Vehicle>>
    @Query("SELECT * FROM VEHICLES WHERE latitude=:latitude and longitude=:longitude")
    fun getVehicleByLatitudeAndLongitude(latitude:Double, longitude:Double):Vehicle

}