package com.example.myapplication.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.myapplication.dao.VehicleDao
import com.example.myapplication.database.MyDatabase

/**
 * This class is our repository. It provides us with a abstraction layer to the access to the Room
 * Database via the DAO.
 */
class VehicleRepository(application: Application) {

    val vehicleDao:VehicleDao?
    val allvehicles:LiveData<List<Vehicle>>?

    init {
        var db=MyDatabase.getInstance(application)
        vehicleDao = db!!.vehicleDao()
        allvehicles = vehicleDao.getAll()
    }

    fun insertVehicle(vehicle:Vehicle){
        InsertAsyncTask(vehicleDao!!).execute(vehicle)
    }

    fun getAll():LiveData<List<Vehicle>>?{
        return allvehicles
    }



}

private class InsertAsyncTask(private val vehicleDao: VehicleDao):AsyncTask<Vehicle, Void,Void>() {
    override fun doInBackground(vararg params: Vehicle): Void? {

        vehicleDao.insertVehicle(vehicle = params[0])
        return null
    }


}
