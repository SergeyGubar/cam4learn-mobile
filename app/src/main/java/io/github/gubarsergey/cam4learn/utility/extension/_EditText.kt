package io.github.gubarsergey.cam4learn.utility.extension

import android.widget.EditText

val EditText.input
    get() = this.text.toString()