package io.github.gubarsergey.cam4learn.utility.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class RuntimePermissionHelper {

    fun checkStorageWritePermissionGranted(activity: Activity): Boolean {
        return checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun requestStorageWritePermission(activity: Activity, requestCode: Int) {
        requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, requestCode)
    }

    private fun checkPermissionGranted(activity: Activity, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(activity: Activity, permission: String, code: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            code
        )
    }
}