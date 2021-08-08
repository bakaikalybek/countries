package kg.bakai.countries.di

import android.app.Application
import android.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import kg.bakai.countries.db.CountriesDao
import kg.bakai.countries.db.CountriesDatabase
import kg.bakai.countries.network.ApiService
import kg.bakai.countries.repository.CountriesRepository
import kg.bakai.countries.ui.CountriesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single { Gson() }
}

val viewModelModules = module {
    viewModel { CountriesViewModel(get()) }
}

val repositoryModules = module {
    single { CountriesRepository(get(), get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): CountriesDatabase {
        return Room.databaseBuilder(application, CountriesDatabase::class.java, "countries")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: CountriesDatabase): CountriesDao {
        return database.countriesDao
    }

    single { provideDatabase(get()) }
    single { provideDao(get()) }
}

val networkModule = module {
    single {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.first.org/data/v1/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}