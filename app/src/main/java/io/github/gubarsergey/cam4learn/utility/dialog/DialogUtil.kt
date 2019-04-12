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
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { _, _ -> positiveCallback() }
            .show()
    }

    fun showSingleChoiceDialog(
        context: Context,
        title: String,
        items: Array<String>,
        positiveCallback: (Int) -> Unit,
        positiveButtonText: String = context.getString(R.string.ok),
        checkedItem: Int = -1
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setSingleChoiceItems(items, checkedItem, null)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                val itemPosition = (dialog as AlertDialog).listView.checkedItemPosition
                positiveCallback(itemPosition)
            }
            .show()
    }
}