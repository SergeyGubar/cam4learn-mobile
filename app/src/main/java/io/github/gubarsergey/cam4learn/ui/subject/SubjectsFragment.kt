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
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_subjects.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class SubjectsFragment : BaseFragment() {

    private val subjectsRepository: SubjectsRepository by inject()
    private val adapter: SubjectsAdapter = SubjectsAdapter()

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
        disposable.safelyDispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.main_menu_teachers -> {
                DialogUtil.showPositiveDialog(notNullContext, getString(R.string.export), getString(R.string.info_subjects_export))
                true
            }
            else -> false
        }
    }

    private fun initRecycler() {
        subjects_recycler.adapter = adapter
        subjects_recycler.layoutManager = LinearLayoutManager(notNullContext)
    }

    private fun loadSubjects() {
        disposable = subjectsRepository.getAllSubjects().subscribeBy(
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
        )
    }
}