package io.github.gubarsergey.cam4learn.utility.extension

import android.content.Intent
import android.support.v7.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> navigate(
    activity: AppCompatActivity,
    block: Intent.() -> Unit = { }
) {
    val intent = Intent(activity, T::class.java).apply(block)
    activity.startActivity(intent)
}

