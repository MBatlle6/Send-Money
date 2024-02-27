package com.example.widgets_compose

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun checkPermissions(activity: MainActivity): Boolean {
    val coarsePermissionState = ActivityCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val finePermissionState = ActivityCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    return (coarsePermissionState == PackageManager.PERMISSION_GRANTED
            || finePermissionState == PackageManager.PERMISSION_GRANTED)
}