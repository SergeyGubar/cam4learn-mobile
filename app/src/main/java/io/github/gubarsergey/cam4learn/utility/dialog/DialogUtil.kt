package io.github.gubarsergey.cam4learn.utility.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import io.github.gubarsergey.cam4learn.R

object DialogUtil {
    fun showPositiveDialog(
        context: Context,
        title: String,
        message: String,
        positiveCallback: () -> Unit,
        positiveButtonText: String = context.getString(R.string.ok)
    ) {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ -> positiveCallback() }
        builder.show()
    }
}