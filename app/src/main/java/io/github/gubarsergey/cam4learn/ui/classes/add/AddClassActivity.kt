package io.github.gubarsergey.cam4learn.ui.classes.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.cam4learn.R

class AddClassActivity : AppCompatActivity() {

    companion object {
        fun makeIntent(context: Context) = Intent(context, AddClassActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)
    }
}