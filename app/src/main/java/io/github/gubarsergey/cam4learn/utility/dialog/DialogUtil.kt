package io.github.gubarsergey.cam4learn.utility.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.dialog_three_options.view.*

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

    @SuppressLint("InflateParams")
    fun showDialogWithThreeOptions(
        context: Context,
        title: String,
        items: Array<String>,
        callbacks: List<() -> Unit>
    ) {
        require(items.size == 3 && callbacks.size == 3) { "Callbacks and items size must be 3!" }
        val view = context.inflater.inflate(R.layout.dialog_three_options, null, false)
        with(view.dialog_three_actions_first) {
            setOnClickListener {
                callbacks[0]()
            }
            text = items[0]
        }
        with(view.dialog_three_actions_second) {
            setOnClickListener {
                callbacks[1]()
            }
            text = items[1]
        }
        with(view.dialog_three_actions_third) {
            setOnClickListener {
                callbacks[2]()
            }
            text = items[2]
        }
        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(view)
            .setPositiveButton(R.string.cancel, null)
            .show()
    }
}