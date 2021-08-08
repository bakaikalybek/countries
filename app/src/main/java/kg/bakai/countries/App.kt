package kg.bakai.countries

import android.app.Application
import kg.bakai.countries.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModules, viewModelModules, repositoryModules, databaseModule, networkModule))
        }
    }
}