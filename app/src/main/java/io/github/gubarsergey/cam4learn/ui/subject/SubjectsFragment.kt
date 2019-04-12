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


private const val REQUEST_CODE_WRITE_STORAGE = 42

class SubjectsFragment : BaseFragment() {

    private val subjectsRepository: SubjectsRepository by inject()
    private val runtimePermissionHelper: RuntimePermissionHelper by inject()
    private val fileHelper: FileHelper by inject()
    private val adapter: SubjectsAdapter = SubjectsAdapter()
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
                DialogUtil.showPositiveDialog(
                    notNullContext,
                    getString(R.string.export),
                    getString(R.string.info_subjects_export),
                    positiveCallback = {
                        if (runtimePermissionHelper.checkStorageWritePermissionGranted(activity!!)) {
                            exportJson()
                        } else {
                            runtimePermissionHelper.requestStorageWritePermission(activity!!, REQUEST_CODE_WRITE_STORAGE)
                        }
                    })
                true
            }
            else -> false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WRITE_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportJson()
            } else {
                toast(getString(R.string.error_export_permission))
            }
        }
    }

    private fun exportJson() {
        compositeDisposable.add(subjectsRepository.getAllSubjectsJson().subscribeBy(
            onError = { error ->
                Timber.w("$error")
            },
            onSuccess = { result ->
                Timber.d("Saving result $result")
                fileHelper.saveContentToFile("test.json", result.string())
            }
        ))
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