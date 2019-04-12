package io.github.gubarsergey.cam4learn.utility.helper

import android.content.Context
import android.os.Environment
import timber.log.Timber
import java.io.File

class FileHelper(private val context: Context) {
    fun saveContentToFile(fileName: String, content: String) {
        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsFolder, fileName)
        file.writeBytes(content.toByteArray())
    }

    fun getPublicAlbumStorageDir(folderName: String): File? {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), folderName
        )
        if (!file?.mkdirs()) {
            Timber.w("Directory not created")
        }
        return file
    }

}