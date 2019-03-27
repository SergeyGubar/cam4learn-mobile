package io.github.gubarsergey.cam4learn.utility.extension

import android.content.Intent

fun filledIntent(block: Intent.() -> Unit): Intent {
    return Intent().apply(block)
}