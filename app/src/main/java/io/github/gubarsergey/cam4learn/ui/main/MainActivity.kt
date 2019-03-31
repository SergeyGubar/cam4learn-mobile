package io.github.gubarsergey.cam4learn.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.entity.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.repository.login.LoginRepository
import io.github.gubarsergey.cam4learn.ui.group.GroupsFragment
import io.github.gubarsergey.cam4learn.ui.subject.SubjectsFragment
import io.github.gubarsergey.cam4learn.utility.extension.inTransaction
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    private val loginRepository: LoginRepository by inject()
    private val prefHelper: SharedPrefHelper by inject()
    private var disposable: Disposable? = null

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
        login()
    }

    private fun login() {
        disposable =
            loginRepository.login(LoginRequestModel(prefHelper.getLogin(), prefHelper.getPassword())).subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        is Result.Success -> {
                            result.value?.let {
                                prefHelper.saveToken(it.token)
                            } ?: Timber.w("login: error response is null")
                        }
                        is Result.Error -> {
                            toast("Authentication failed!")
                            Timber.w("login: error [${result.errorCode}]")
                        }
                    }
                },
                onError = {
                    toast("Authentication failed!")
                    Timber.w("login: error [${it.localizedMessage}]")
                }
            )
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
