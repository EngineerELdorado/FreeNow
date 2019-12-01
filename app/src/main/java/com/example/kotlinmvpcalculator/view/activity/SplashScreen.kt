package com.example.kotlinmvpcalculator.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinmvpcalculator.R
import com.example.kotlinmvpcalculator._network.VehicleService
import com.example.kotlinmvpcalculator._network.VehicleServiceBuilder
import com.example.kotlinmvpcalculator.models.ApiResponse
import com.example.myapplication.viewmodel.VehiclePresenter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        //4second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this, MainActivity::class.java))
            //finish this activity
            finish()
        },3000)
    }




}
