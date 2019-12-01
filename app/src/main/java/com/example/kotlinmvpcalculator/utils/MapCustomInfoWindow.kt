package com.example.kotlinmvpcalculator.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.kotlinmvpcalculator.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class MapCustomInfoWindow(val context: Context) : InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window, null)
//        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?
//
//        mInfoView.txtLocMarkerName.text = mInfoWindow?.mLocatioName
//        mInfoView.txtLocMarkerAddress.text = mInfoWindow?.mLocationAddres
//        mInfoView.txtLocMarkerEmail.text = mInfoWindow?.mLocationEmail
//        mInfoView.txtLocMarkerPhone.text = mInfoWindow?.mLocationPhone
//        mInfoView.txtOpenningHoursValue.text = mInfoWindow?.mLocationHours
//        mInfoView.txtBranchMarkerValue.text = mInfoWindow?.mLocationBranch

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}