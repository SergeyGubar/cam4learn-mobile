package io.github.gubarsergey.cam4learn.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.ui.group.GroupsFragment
import io.github.gubarsergey.cam4learn.ui.subject.SubjectsFragment
import io.github.gubarsergey.cam4learn.utility.extension.inTransaction
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }
        main_navigation_view.setNavigationItemSelectedListener { menuItem ->
            handleItemSelected(menuItem)
        }
    }

    private fun handleItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        main_drawer_layout.closeDrawers()
        val fragment = when (menuItem.itemId) {
            R.id.nav_groups -> GroupsFragment.newInstance()
            R.id.nav_subjects -> SubjectsFragment.newInstance()
            R.id.nav_teachers -> SubjectsFragment.newInstance()
            else -> throw IllegalStateException("Menu item $menuItem does not exist!")
        }
        supportFragmentManager.inTransaction {
            replace(R.id.main_container, fragment)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                main_drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
