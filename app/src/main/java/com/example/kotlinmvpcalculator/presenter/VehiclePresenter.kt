package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.myapplication.repository.VehicleRepository

/**
 * This class serves as the middle man between my View and my Model.
 * It is my ViewModel(MVVM architecture) class but depending on the architecture it can also
 * be a presenter and follow the MVP architecture
 */
class VehiclePresenter(application: Application):AndroidViewModel(application) {

    private val vehicleRepository:VehicleRepository
    private val allVehicles:LiveData<List<Vehicle>>?

    init {
        vehicleRepository = VehicleRepository(application)
        allVehicles =vehicleRepository.getAll()
    }

    fun insertVehicle(vehicle: Vehicle){
        vehicleRepository.insertVehicle(vehicle)
    }

    fun getAllVehicles():LiveData<List<Vehicle>>?{

        return vehicleRepository.getAll()
    }

}