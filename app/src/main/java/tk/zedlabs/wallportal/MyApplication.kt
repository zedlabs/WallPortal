package tk.zedlabs.wallportal

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tk.zedlabs.wallportal.di.databaseModule
import tk.zedlabs.wallportal.di.viewModelModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(arrayListOf(databaseModule, viewModelModule))
        }
    }
}