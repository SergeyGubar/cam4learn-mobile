package io.github.gubarsergey.cam4learn.ui.login

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.entity.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.repository.login.LoginRepository
import io.github.gubarsergey.cam4learn.ui.main.MainActivity
import io.github.gubarsergey.cam4learn.utility.extension.addSimpleTextChangedListener
import io.github.gubarsergey.cam4learn.utility.extension.input
import io.github.gubarsergey.cam4learn.utility.extension.navigate
import io.github.gubarsergey.cam4learn.utility.extension.safelyDispose
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.github.gubarsergey.cam4learn.utility.validator.CredentialsValidator
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val loginRepository: LoginRepository by inject()
    private val prefHelper: SharedPrefHelper by inject()
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkIfUserIsLoggedIn { navigate<MainActivity>(this); finish() }
        setupListeners()
    }

    override fun onStop() {
        super.onStop()
        disposable.safelyDispose()
    }

    private fun checkIfUserIsLoggedIn(isUserLoggedAction: () -> Unit) {
        if (prefHelper.isUserLoggedIn()) isUserLoggedAction.invoke()
    }

    private fun setupListeners() {
        login_login_button.setOnClickListener {
            handleLogin(login_email_edit_text.input, login_password_edit_text.input)
        }
        login_email_edit_text.addSimpleTextChangedListener {
            login_email_text_input_layout.error = null
        }
        login_password_edit_text.addSimpleTextChangedListener {
            login_password_text_input_layout.error = null
        }
    }

    private fun handleLogin(login: String, password: String) {
        Timber.d("handleLogin: login = [$login], password = [$password]")
        val isEmailValid = CredentialsValidator.isEmailValid(login)
        val isPasswordValid = CredentialsValidator.isPasswordValid(password)
        if (isEmailValid && isPasswordValid) {
            disposable = loginRepository.login(
                LoginRequestModel(login, password)
            ).subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        is Result.Success -> {
                            Timber.d("handleLogin: result success [$result]")
                            val token = result.value?.token
                            token?.let {
                                with(prefHelper) {
                                    saveToken(token)
                                    saveLogin(login)
                                    savePassword(password)
                                    setUserLoggedIn(true).also { navigate<MainActivity>(this@LoginActivity) }
                                }
                            } ?: Timber.w("handleLogin: result error: success response, but no token")
                        }
                        is Result.Error -> {
                            Timber.w("handleLogin: result error [${result.errorCode}]")
                            toast("Login failed!")
                        }
                    }
                },
                onError = {
                    Timber.w("handleLogin: result error [${it.localizedMessage}]")
                }
            )
            return
        }
        handleLoginError(isEmailValid, isPasswordValid)
    }

    private fun handleLoginError(emailValid: Boolean, passwordValid: Boolean) {
        Timber.d("handleLoginError: emailValid = [$emailValid], passwordValid = [$passwordValid]")
        if (!emailValid) {
            login_email_text_input_layout.error = getString(R.string.error_email_validation)
        }
        if (!passwordValid) {
            login_password_text_input_layout.error = getString(R.string.error_password_validation)
        }
    }
}
