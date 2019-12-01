package com.example.kotlinmvpcalculator.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.freenow.BottomSheetFragment
import com.example.freenow.Constants.HAMBURG_LATITUDE
import com.example.freenow.Constants.HAMBURG_LONGITUDE
import com.example.freenow.VehicleAdapter
import com.example.kotlinmvpcalculator.R
import com.example.kotlinmvpcalculator._network.VehicleService
import com.example.kotlinmvpcalculator._network.VehicleServiceBuilder
import com.example.kotlinmvpcalculator.models.ApiResponse
import com.example.kotlinmvpcalculator.models.Coordinate
import com.example.kotlinmvpcalculator.models.Vehicle
import com.example.kotlinmvpcalculator.utils.MapCustomInfoWindow
import com.example.myapplication.viewmodel.VehiclePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.vehicle.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    var detailShowing:Boolean =false
    val bottomSheetFragment = BottomSheetFragment()
    val hamburg = LatLng(HAMBURG_LATITUDE, HAMBURG_LONGITUDE)
    private lateinit var vehiclePresenter:VehiclePresenter
    private lateinit var vehicleAdapter: VehicleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        vehicleAdapter = VehicleAdapter(context = applicationContext)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        /**
         * We can subscribe to the Observable created in our adapter.
         * on each item click from the recycler view the obsevable emits the clicked vehicle.
         * We subscribe to it here so that we can zoom it out on the map.
         */
        var obs = vehicleAdapter.clickEvent()
        obs.subscribe({
            zoom(it,true)
        })

        vehiclePresenter = ViewModelProviders.of(this).get(VehiclePresenter::class.java)

        viewAsListBtn.setOnClickListener { v: View ->
            run {
                if (detailShowing) {
                    mMap.clear()
                    onMapReady(mMap)
                    loadData()
                    detailShowing = false;
                    viewAsListBtn.setText(resources.getString(R.string.view_as_list))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hamburg, 11.5f))
                } else {
                    callBottomDialog()
                }
            }
        }

        loadData()

    }

    fun callBottomDialog(){

        bottomSheetFragment.show(supportFragmentManager,bottomSheetFragment.tag)
    }

    private fun loadData() {
       var call = VehicleServiceBuilder.builderService(VehicleService::class.java).getAll()

        call.enqueue(object : Callback<ApiResponse>{
            override fun onFailure(call: Call<ApiResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ApiResponse>?, response: Response<ApiResponse>?) {

              if (response!!.isSuccessful){
                  var vehicles = response.body().vehicles;

                  for (vehicle in vehicles!!){
                     var latitude=vehicle.coordinate!!.latitude
                      var longitude=vehicle.coordinate!!.longitude
                      vehiclePresenter.insertVehicle(vehicle)
                      val bbsr = LatLng(latitude, longitude)
                      val marker = MarkerOptions().position(bbsr)
                          .title(getString(R.string.TEXT_ID)+vehicle.id.toString()+getString(
                              R.string.TEXT_TYPE)+vehicle.fleetType)
                      marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
                      mMap.addMarker(marker)
                  }
              }
            }


        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Hamburg and move the camera

        val circleOptions = CircleOptions()
        circleOptions.center(hamburg)
        circleOptions.radius(150.0)
        circleOptions.strokeColor(resources.getColor(R.color.colorPrimary))
        circleOptions.fillColor(resources.getColor(R.color.colorPrimary))
        circleOptions.strokeWidth(3f)
        mMap.addCircle(circleOptions)
        var marker: Marker = mMap.addMarker(MarkerOptions().position(hamburg).title(getString(R.string.drag_to_see_more)))
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hamburg, 11.0f))
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        marker.setDraggable(true)
        var mapCustomInfoWindow = MapCustomInfoWindow(applicationContext)
        //mMap.setInfoWindowAdapter(mapCustomInfoWindow)
        marker.showInfoWindow()



    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        detailShowing = true
        var vehicle = Vehicle()
        var coordinate = Coordinate()
        coordinate.latitude =marker!!.position.latitude
        coordinate.longitude =marker!!.position.longitude
        vehicle.coordinate =coordinate
        title = marker.title
        zoom(vehicle,false)
        return true
    }

    fun zoom(it: Vehicle, hideFragment:Boolean) {

        if (hideFragment){
            title = "ID: "+it.id.toString()+" TYPE: "+it.fleetType
            bottomSheetFragment.dismiss()
        }
        detailShowing = true;
        viewAsListBtn.setText("<---Go Back---")
        val position = LatLng(it.coordinate!!.latitude, it.coordinate!!.longitude)
        var marker:Marker = mMap.addMarker(MarkerOptions().position(position).title(title as String))
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.taxi))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0f))
        val circleOptions = CircleOptions()
        circleOptions.center(position)
        circleOptions.radius(100.0)
        circleOptions.strokeColor(resources.getColor(R.color.colorPrimary))
        circleOptions.fillColor(Color.argb(70,150,50,50))
        circleOptions.strokeWidth(3f)
        mMap.addCircle(circleOptions)
        marker.showInfoWindow()
    }
}
