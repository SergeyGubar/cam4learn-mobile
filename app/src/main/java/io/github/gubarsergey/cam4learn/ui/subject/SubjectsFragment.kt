package io.github.gubarsergey.cam4learn.ui.subject

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.repository.subject.SubjectsRepository
import io.github.gubarsergey.cam4learn.ui.BaseFragment
import io.github.gubarsergey.cam4learn.utility.dialog.DialogUtil
import io.github.gubarsergey.cam4learn.utility.extension.notNullContext
import io.github.gubarsergey.cam4learn.utility.extension.safelyDispose
import io.github.gubarsergey.cam4learn.utility.helper.FileHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_subjects.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber
import android.content.pm.PackageManager
import io.github.gubarsergey.cam4learn.utility.helper.RuntimePermissionHelper
import java.lang.IllegalStateException


private const val REQUEST_CODE_WRITE_JSON = 42
private const val REQUEST_CODE_WRITE_CSV = 43
private const val EXPORT_JSON_POSITION = 0
private const val EXPORT_CSV_POSITION = 1

class SubjectsFragment : BaseFragment() {

    private val subjectsRepository: SubjectsRepository by inject()
    private val runtimePermissionHelper: RuntimePermissionHelper by inject()
    private val fileHelper: FileHelper by inject()
    private val adapter: SubjectsAdapter = SubjectsAdapter(::onSubjectClicked)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(): SubjectsFragment {
            return SubjectsFragment()
        }
    }

    override fun getTitle(): String? = getString(R.string.subjects)
    override val layout: Int = R.layout.fragment_subjects

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSubjects()
        initRecycler()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.safelyDispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_menu_teachers -> {
                showExportDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                REQUEST_CODE_WRITE_CSV -> exportCsv()
                REQUEST_CODE_WRITE_JSON -> exportJson()
            }
        } else {
            toast(getString(R.string.error_export_permission))
        }
    }

    private fun onSubjectClicked(id: String) {
        Timber.d("onSubjectClicked: id = [$id]")
    }

    private fun showExportDialog() {
        with(notNullContext) {
            DialogUtil.showSingleChoiceDialog(
                this,
                getString(R.string.export),
                arrayOf(getString(R.string.json), getString(R.string.csv)),
                { position ->
                    if (runtimePermissionHelper.checkStorageWritePermissionGranted(activity!!)) {
                        when (position) {
                            EXPORT_JSON_POSITION -> exportJson()
                            EXPORT_CSV_POSITION -> exportCsv()
                        }
                    } else {
                        val code = when (position) {
                            EXPORT_JSON_POSITION -> REQUEST_CODE_WRITE_JSON
                            EXPORT_CSV_POSITION -> REQUEST_CODE_WRITE_CSV
                            else -> throw IllegalStateException("Position $position unsupported")
                        }
                        runtimePermissionHelper.requestStorageWritePermission(activity!!, code)
                    }
                }
            )
        }
    }

    private fun exportJson() {
        compositeDisposable.add(subjectsRepository.getAllSubjectsJson().subscribeBy(
            onError = { error ->
                toast(getString(R.string.error_export_json))
                Timber.w("$error")
            },
            onSuccess = { result ->
                toast(getString(R.string.export_json_success))
                Timber.d("Saving result $result")
                fileHelper.saveContentToFile("teacher-subjects.json", result.string())
            }
        ))
    }

    private fun exportCsv() {
        toast(getString(R.string.error_export_csv))
        // TODO
    }

    private fun initRecycler() {
        subjects_recycler.adapter = adapter
        subjects_recycler.layoutManager = LinearLayoutManager(notNullContext)
    }

    private fun loadSubjects() {
        compositeDisposable.add(subjectsRepository.getAllSubjects().subscribeBy(
            onSuccess = { result ->
                when (result) {
                    is Result.Success -> {
                        result.value?.let {
                            adapter.swapData(it)
                        } ?: Timber.w("request was successful, but value is null")
                    }
                    is Result.Error -> {
                        Timber.w("")
                        toast("Subjects load failed. Please try again.")
                    }
                }
            },
            onError = {
                Timber.w(("error [${it.localizedMessage}]"))
            }
        ))
    }
}