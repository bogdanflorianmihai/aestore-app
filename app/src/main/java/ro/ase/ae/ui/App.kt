package ro.ase.ae.ui

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import net.mready.photon.Injector
import net.mready.photon.Provides
import ro.ase.ae.api.ApiModule
import javax.inject.Singleton

class App : Application() {

    lateinit var injector: Injector

    override fun onCreate() {
        super.onCreate()

        injector = Injector.with(AndroidModule(this), ApiModule)

        AndroidThreeTen.init(this)
    }
}

fun Context.injector() = (applicationContext as App).injector

class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    fun getApplication(): Application = application

    @Provides
    @Singleton
    fun getContext(): Context = application
}