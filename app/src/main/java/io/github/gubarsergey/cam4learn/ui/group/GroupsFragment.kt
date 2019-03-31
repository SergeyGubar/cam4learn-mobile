package io.github.gubarsergey.cam4learn.ui.group

import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.ui.BaseFragment

class GroupsFragment: BaseFragment() {

    companion object {
        fun newInstance(): GroupsFragment {
            return GroupsFragment()
        }
    }

    override fun getTitle(): String? = getString(R.string.groups)
    override val layout: Int = R.layout.fragment_groups
}