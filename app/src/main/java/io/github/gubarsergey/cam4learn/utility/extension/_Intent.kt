package io.github.gubarsergey.cam4learn.utility.extension

import android.content.Context
import android.content.Intent

fun filledIntent(block: Intent.() -> Unit): Intent {
    return Intent().apply(block)
}

inline fun <reified T> filledIntentFor(
    context: Context,
    block: Intent.() -> Unit
): Intent {
    return Intent(context, T::class.java).apply(block)
}