package io.github.gubarsergey.cam4learn.ui.classes

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.github.gubarsergey.cam4learn.network.repository.classes.ClassesRepository
import io.github.gubarsergey.cam4learn.ui.BaseFragment
import io.github.gubarsergey.cam4learn.ui.classes.add.AddClassActivity
import io.github.gubarsergey.cam4learn.ui.classes.edit.EditClassActivity
import io.github.gubarsergey.cam4learn.ui.classes.student.ClassStudentsActivity
import io.github.gubarsergey.cam4learn.utility.dialog.DialogUtil
import io.github.gubarsergey.cam4learn.utility.extension.notNullContext
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_classes.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class ClassesFragment : BaseFragment() {

    companion object {
        private const val NEW_DATA_REQUEST_CODE = 404
        fun newInstance() = ClassesFragment()
    }

    override val layout: Int = R.layout.fragment_classes
    override fun getTitle(): String? = getString(R.string.classes)
    private val adapter = ClassesAdapter(onLongClick = ::onLongItemClassClick)
    private val repository: ClassesRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        loadData()
        setupListeners()
    }

    private fun setupListeners() {
        classes_fab.setOnClickListener {
            startActivityForResult(AddClassActivity.makeIntent(notNullContext), NEW_DATA_REQUEST_CODE)
        }
    }

    private fun setupRecycler() {
        classes_recycler.adapter = adapter
        classes_recycler.layoutManager = LinearLayoutManager(notNullContext)
    }

    private fun loadData() {
        disposable = repository.getAllClasses().subscribeBy(
            onError = { Timber.w("error: ${it.localizedMessage}") },
            onSuccess = { result ->
                result.fold(
                    { response ->
                        Timber.d("success: $response")
                        adapter.swapData(response)
                    },
                    { Timber.w("error: ${it.localizedMessage}") }
                )
            }
        )
    }

    private fun onLongItemClassClick(classResponseModel: ClassResponseModel) {
        DialogUtil.showPositiveDialog(
            notNullContext,
            getString(R.string.delete_class_confirm),
            getString(R.string.this_action_cannot_be_undone),
            {
                disposable = repository.deleteClass(classResponseModel.id)
                    .subscribeBy(
                        onError = { toast(it.localizedMessage) },
                        onSuccess = { adapter.removeItem(classResponseModel.id) }
                    )
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_DATA_REQUEST_CODE) {
            loadData()
        }
    }
}