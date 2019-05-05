package io.github.gubarsergey.cam4learn

import android.app.Application
import com.google.gson.GsonBuilder
import io.github.gubarsergey.cam4learn.network.api.*
import io.github.gubarsergey.cam4learn.network.constant.NetworkConstants
import io.github.gubarsergey.cam4learn.network.repository.classes.ClassesRepository
import io.github.gubarsergey.cam4learn.network.repository.group.GroupsRepository
import io.github.gubarsergey.cam4learn.network.repository.lector.LectorsRepository
import io.github.gubarsergey.cam4learn.network.repository.login.LoginRepository
import io.github.gubarsergey.cam4learn.network.repository.room.RoomRepository
import io.github.gubarsergey.cam4learn.network.repository.statistic.SubjectStatisticRepository
import io.github.gubarsergey.cam4learn.network.repository.subject.SubjectsRepository
import io.github.gubarsergey.cam4learn.utility.helper.FileHelper
import io.github.gubarsergey.cam4learn.utility.helper.RuntimePermissionHelper
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
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }

    private val loginModule = module {
        single { get<Retrofit>().create(LoginApi::class.java) }
        single { LoginRepository(get()) }
    }

    private val utilsModule = module {
        single { SharedPrefHelper(get()) }
        single { FileHelper(get()) }
        single { RuntimePermissionHelper() }
    }

    private val subjectsModule = module {
        single { SubjectsRepository(get(), get()) }
        single { get<Retrofit>().create(SubjectsApi::class.java) }
    }

    private val lectorsModule = module {
        single { get<Retrofit>().create(LectorsApi::class.java) }
        single { LectorsRepository(get(), get()) }
    }

    private val subjectStatisticModule = module {
        single { get<Retrofit>().create(SubjectStatisticApi::class.java) }
        single { SubjectStatisticRepository(get(), get()) }
    }

    private val classesModule = module {
        single { get<Retrofit>().create(ClassesApi::class.java) }
        single { ClassesRepository(get(), get()) }
    }

    private val groupsModule = module {
        single { get<Retrofit>().create(GroupsApi::class.java) }
        single { GroupsRepository(get(), get()) }
    }

    private val roomsModule = module {
        single { get<Retrofit>().create(RoomsApi::class.java) }
        single { RoomRepository(get(), get()) }

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
                subjectsModule,
                lectorsModule,
                subjectStatisticModule,
                classesModule,
                groupsModule,
                roomsModule
            )
            androidContext(this@App)
        }
    }
}