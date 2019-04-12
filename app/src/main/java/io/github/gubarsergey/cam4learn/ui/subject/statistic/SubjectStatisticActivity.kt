package io.github.gubarsergey.cam4learn.ui.subject.statistic

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.gubarsergey.cam4learn.R

class SubjectStatisticActivity : AppCompatActivity() {


    companion object {
        private const val SUBJECT_ID_EXTRA_KEY = "SUBJECT_ID_EXTRA_KEY"
        fun makeIntent(context: Context, subjectId: Int): Intent {
            return Intent(context, SubjectStatisticActivity::class.java).apply {
                putExtra(SUBJECT_ID_EXTRA_KEY, subjectId)
            }
        }
    }

    private val subjectId: Int by lazy { intent.getIntExtra(SUBJECT_ID_EXTRA_KEY, 0) }
    private val adapter = SubjectStatisticAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_statistic)
    }
}
