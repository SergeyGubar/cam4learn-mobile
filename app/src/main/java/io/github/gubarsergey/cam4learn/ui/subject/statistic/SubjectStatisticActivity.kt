package io.github.gubarsergey.cam4learn.ui.subject.statistic

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.repository.statistic.SubjectStatisticRepository
import io.github.gubarsergey.cam4learn.utility.dialog.DialogUtil
import io.github.gubarsergey.cam4learn.utility.extension.safelyDispose
import io.github.gubarsergey.cam4learn.utility.helper.FileHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_subject_statistic.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*


private const val EXPORT_JSON_POSITION = 0
private const val EXPORT_CSV_POSITION = 1

class SubjectStatisticActivity : AppCompatActivity() {

    companion object {
        private const val SUBJECT_ID_EXTRA_KEY = "SUBJECT_ID_EXTRA_KEY"
        fun makeIntent(context: Context, subjectId: Int): Intent {
            return Intent(context, SubjectStatisticActivity::class.java).apply {
                putExtra(SUBJECT_ID_EXTRA_KEY, subjectId)
            }
        }
    }

    private var disposable: Disposable? = null

    private val subjectId: Int by lazy { intent.getIntExtra(SUBJECT_ID_EXTRA_KEY, 0) }
    private val adapter = SubjectStatisticAdapter()
    private val mapper = SubjectStatisticUIMapper()
    private val compositeDisposable = CompositeDisposable()
    private val repository: SubjectStatisticRepository by inject()
    private val fileHelper: FileHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_statistic)
        setSupportActionBar(main_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        initRecycler()
        loadSubjectStatistic()
    }

    override fun onStop() {
        super.onStop()
        disposable.safelyDispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.main_menu_teachers -> {
                showExportDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showExportDialog() {
        with(this) {
            DialogUtil.showSingleChoiceDialog(
                this,
                getString(R.string.export),
                arrayOf(getString(R.string.json), getString(R.string.csv)),
                { position ->
                    when (position) {
                        EXPORT_JSON_POSITION -> exportJson()
                        EXPORT_CSV_POSITION -> exportCsv()
                    }
                }
            )
        }
    }

    private fun exportCsv() {
        compositeDisposable.add(repository.getStatisticCsv(subjectId).subscribeBy(
            onError = { error ->
                toast(getString(R.string.error_export_csv))
                Timber.w("$error")
            },
            onSuccess = { result ->
                toast(getString(R.string.export_csv_success))
                Timber.d("Saving result $result")
                fileHelper.saveContentToFile("subject-statistic${Calendar.getInstance().time}.csv", result.string())
            }
        ))
    }

    private fun exportJson() {
        compositeDisposable.add(repository.getStatisticJson(subjectId).subscribeBy(
            onError = { error ->
                toast(getString(R.string.error_export_json))
                Timber.w("$error")
            },
            onSuccess = { result ->
                toast(getString(R.string.export_json_success))
                Timber.d("Saving result $result")
                fileHelper.saveContentToFile(
                    "subject-statistic${Calendar.getInstance().time}.json",
                    result.string()
                )
            }
        ))
    }

    private fun initRecycler() {
        subject_statistic_recycler.adapter = adapter
        subject_statistic_recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun loadSubjectStatistic() {
        disposable = repository.getStatistic(subjectId)
            .subscribeBy(
                onError = { error ->
                    Timber.w("$error")
                    toast(getString(R.string.error_load_subject_statistic))
                },
                onSuccess = { result ->
                    when (result) {
                        is Result.Success -> {
                            Timber.d(result.value.toString())
                            val uiData = result.value!!.flatMap { subjectStatisticResponseModel ->
                                mapper.toUIModel(subjectStatisticResponseModel)
                            }
                            adapter.swapData(uiData)
                        }
                        is Result.Error -> {
                            toast(getString(R.string.error_load_subject_statistic))
                            Timber.w(result.errorCode)
                        }
                    }
                }
            )
    }
}
