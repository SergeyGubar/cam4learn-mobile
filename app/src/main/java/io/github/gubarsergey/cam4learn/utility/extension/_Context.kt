package io.github.gubarsergey.cam4learn.utility.extension

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater

inline fun <reified T : AppCompatActivity> navigate(
    activity: AppCompatActivity,
    block: Intent.() -> Unit = { }
) {
    val intent = Intent(activity, T::class.java).apply(block)
    activity.startActivity(intent)
}

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
