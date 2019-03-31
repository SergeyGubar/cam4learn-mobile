package io.github.gubarsergey.cam4learn

import android.app.Application
import com.google.gson.GsonBuilder
import io.github.gubarsergey.cam4learn.network.api.LoginApi
import io.github.gubarsergey.cam4learn.network.api.SubjectsApi
import io.github.gubarsergey.cam4learn.network.constant.NetworkConstants
import io.github.gubarsergey.cam4learn.network.repository.login.LoginRepository
import io.github.gubarsergey.cam4learn.network.repository.subject.SubjectsRepository
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class App : Application() {

    private val networkModule = module {
        single {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }
        single {
            GsonBuilder()
                .setLenient()
                .create()
        }
        single {
            Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URl)
                .client(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }
    }

    private val loginModule = module {
        single {
            get<Retrofit>().create(LoginApi::class.java)
        }
        single {
            LoginRepository(get())
        }
    }

    private val utilsModule = module {
        single {
            SharedPrefHelper(get())
        }
    }

    private val subjectsModule = module {
        single {
            SubjectsRepository(get(), get())
        }
        single {
            get<Retrofit>().create(SubjectsApi::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            modules(
                networkModule,
                loginModule,
                utilsModule,
                subjectsModule
            )
            androidContext(this@App)
        }
    }
}