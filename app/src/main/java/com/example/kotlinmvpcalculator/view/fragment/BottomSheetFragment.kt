package com.example.freenow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvpcalculator.R
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.myapplication.viewmodel.VehiclePresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * This is a fragment that holds the list of vehile.
 * It pops up from the bottom of the activity once called.
 * */
class BottomSheetFragment: BottomSheetDialogFragment() {


    private lateinit var recyclerView: RecyclerView
   private lateinit var vehicleAdapter:VehicleAdapter
    var vehicles:List<Vehicle> = mutableListOf()
    private lateinit var vehiclePresenter: VehiclePresenter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        var view = inflater.inflate(R.layout.bottom_sheet, container, false)
        var linearLayout = LinearLayoutManager(context)

        vehiclePresenter = ViewModelProviders.of(this).get(VehiclePresenter::class.java)
        vehicleAdapter = VehicleAdapter(context)
        vehicleAdapter.setVehicles(vehicles)

        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration( DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayout)
        recyclerView.setAdapter(vehicleAdapter)

        /**
         * For populating the list of vehicle, I am calling my presenter which calls
         * the repository and fetches data that has been saved on the initial load
         * of the application. the return data come in the form of LiveData and
         * as live data is reactive in nature, we have to subscribe to it in order
         * to obtain the actual data
         */
        vehiclePresenter.getAllVehicles()!!.observe(this, Observer { vehicles->
            run {

                vehicles?.let { vehicleAdapter!!.setVehicles(vehicles) }
            }
        })

        return view
    }

}