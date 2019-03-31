package io.github.gubarsergey.cam4learn.ui.teacher

import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.ui.BaseFragment

class TeachersFragment: BaseFragment() {

    companion object {
        fun newInstance(): TeachersFragment {
            return TeachersFragment()
        }
    }

    override fun getTitle(): String? = getString(R.string.teachers)
    override val layout: Int = R.layout.fragment_teachers
}