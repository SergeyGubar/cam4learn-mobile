package io.github.gubarsergey.cam4learn.ui.teacher

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.repository.lector.LectorsRepository
import io.github.gubarsergey.cam4learn.ui.BaseFragment
import io.github.gubarsergey.cam4learn.utility.dialog.DialogUtil
import io.github.gubarsergey.cam4learn.utility.extension.notNullContext
import io.github.gubarsergey.cam4learn.utility.extension.safelyDispose
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_teachers.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class LectorsFragment : BaseFragment() {

    companion object {
        fun newInstance(): LectorsFragment {
            return LectorsFragment()
        }
    }

    override fun getTitle(): String? = getString(R.string.teachers)
    override val layout: Int = R.layout.fragment_teachers

    private val lectorsRepository: LectorsRepository by inject()
    private val adapter = LectorsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTeachers()
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
                DialogUtil.showPositiveDialog(notNullContext, getString(R.string.export), getString(R.string.info_lectors_export))
                true
            }
            else -> false
        }
    }

    private fun initRecycler() {
        lectors_recycler.adapter = adapter
        lectors_recycler.layoutManager = LinearLayoutManager(notNullContext)
    }

    private fun loadTeachers() {
        disposable = lectorsRepository.getLectors().subscribeBy(
            onSuccess = { result ->
                when (result) {
                    is Result.Success -> {
                        result.value?.let { lectorsList ->
                            adapter.swapData(lectorsList)
                        } ?: Timber.w("Request is successful, but response is null")
                    }
                    is Result.Error -> {
                        Timber.w(result.errorCode)
                    }
                }
            },
            onError = { error ->
                Timber.w(error.localizedMessage)
            }
        )
    }


}