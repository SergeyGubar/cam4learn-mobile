package io.github.gubarsergey.cam4learn.utility.extension

import io.reactivex.disposables.Disposable

fun Disposable?.safelyDispose() {
    this?.let {
        if (!it.isDisposed) {
            it.dispose()
        }
    }
}