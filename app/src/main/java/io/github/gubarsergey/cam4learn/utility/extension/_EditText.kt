package io.github.gubarsergey.cam4learn.utility.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

val EditText.input
    get() = this.text.toString()

fun EditText.addSimpleTextChangedListener(listener: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) = Unit

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }

    })
}