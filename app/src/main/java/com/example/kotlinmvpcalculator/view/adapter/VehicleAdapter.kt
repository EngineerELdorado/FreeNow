package com.example.freenow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freenow.VehicleAdapter.VehicleViewHolder
import com.example.kotlinmvpcalculator.R
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.kotlinmvpcalculator.view.activity.MainActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class VehicleAdapter(private var context: Context?) : RecyclerView.Adapter<VehicleViewHolder>() {

    private lateinit var vehicles:List<Vehicle>
    private val subject= PublishSubject.create<Vehicle>()
    fun setVehicles(vehicles2:List<Vehicle>){
        vehicles = vehicles2
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {

         var view:View =LayoutInflater.from(parent.context).inflate(R.layout.vehicle, parent, false)

        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.createUI(vehicles[position])
        holder.itemView.setOnClickListener {
            /**
             * I can pass this data either through a direct call to my activity zomm Method
             * Or I can push it to an observavle then on my activity I subscribe to the observable
             * and get the emitted value
             */

            /** OPTION 1 direct call to my zoom method*/
            (context as MainActivity).zoom(vehicles[position],true)

            /** OPTION 2 pushing the item to the observable*/
            //subject.onNext(vehicles[position])
        }
    }

    /** As I have made my PublishSubject private I can access its observable through this method */
    fun clickEvent():Observable<Vehicle>{
                return subject
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

     inner class VehicleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.vehicleId)
         var fleetType: TextView = itemView.findViewById(R.id.fleetType)
         fun createUI(vehicle: Vehicle) {
             id.text =vehicle.id.toString()
             fleetType.text = vehicle.fleetType
         }

     }

}