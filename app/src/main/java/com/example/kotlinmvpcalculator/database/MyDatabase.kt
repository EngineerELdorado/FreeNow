package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.myapplication.dao.VehicleDao

/**
 * This class create for us a ROOM databse for local persistence of our data
 */
@Database(entities = [Vehicle::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    companion object{

        @Volatile
        var myDatabase: MyDatabase?=null

        fun getInstance(context: Context): MyDatabase?{

            if (myDatabase==null){
                synchronized(RoomDatabase::class.java){
                    if (myDatabase==null){

                        myDatabase=Room.databaseBuilder(context.applicationContext,MyDatabase::class.java,"VEHICLE_DB" ).build()
                    }
                }
            }
            return myDatabase
        }
    }
}