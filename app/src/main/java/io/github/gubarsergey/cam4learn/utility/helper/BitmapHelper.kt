package io.github.gubarsergey.cam4learn.utility.helper

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object BitmapHelper {
    fun encodeToBase64(bitmap: Bitmap): String {
        val byteOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream)
        val byteArray = byteOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}