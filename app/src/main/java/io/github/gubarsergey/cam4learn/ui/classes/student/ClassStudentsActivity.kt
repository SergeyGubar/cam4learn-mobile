package io.github.gubarsergey.cam4learn.ui.classes.student

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.cam4learn.R

class ClassStudentsActivity: AppCompatActivity() {

    companion object {
        fun makeIntent(context: Context) = Intent(context, ClassStudentsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_students)
    }
}