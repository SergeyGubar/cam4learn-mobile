package io.github.gubarsergey.cam4learn.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {
    abstract val layout: Int
    abstract fun getTitle(): String?

    protected var disposable: Disposable? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        val title = getTitle()
        title?.let {
            activity?.title = it
        }
    }
}