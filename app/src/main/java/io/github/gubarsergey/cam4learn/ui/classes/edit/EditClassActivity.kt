package io.github.gubarsergey.cam4learn.ui.classes.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.cam4learn.R

class EditClassActivity : AppCompatActivity() {

    companion object {
        private const val CLASS_ID = "CLASS_ID"
        fun makeIntent(context: Context, classId: String) = Intent(context, EditClassActivity::class.java).apply {
            putExtra(CLASS_ID, classId)
        }
    }

    private val classId by lazy { intent.getStringExtra(CLASS_ID) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_class)
    }
}