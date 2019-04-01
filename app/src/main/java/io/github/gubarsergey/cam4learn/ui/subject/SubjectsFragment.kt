package io.github.gubarsergey.cam4learn.ui.subject

import android.os.Bundle
import android.view.View
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.repository.subject.SubjectsRepository
import io.github.gubarsergey.cam4learn.ui.BaseFragment
import io.github.gubarsergey.cam4learn.utility.extension.safelyDispose
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class SubjectsFragment : BaseFragment() {

    private val subjectsRepository: SubjectsRepository by inject()

    companion object {
        fun newInstance(): SubjectsFragment {
            return SubjectsFragment()
        }
    }

    override fun getTitle(): String? = getString(R.string.subjects)
    override val layout: Int = R.layout.fragment_subjects

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSubjects()
    }

    override fun onStop() {
        super.onStop()
        disposable.safelyDispose()
    }

    private fun loadSubjects() {
        disposable = subjectsRepository.getAllSubjects().subscribeBy(

            onSuccess = { result ->
                when (result) {
                    is Result.Success -> {
                        result.value?.let {
                            it.forEach { Timber.d("subject = [$it]") }
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