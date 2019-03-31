package io.github.gubarsergey.cam4learn.ui.subject

import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.ui.BaseFragment

class SubjectsFragment: BaseFragment() {
    companion object {
        fun newInstance(): SubjectsFragment {
            return SubjectsFragment()
        }
    }
    override fun getTitle(): String? = getString(R.string.subjects)
    override val layout: Int = R.layout.fragment_subjects
}