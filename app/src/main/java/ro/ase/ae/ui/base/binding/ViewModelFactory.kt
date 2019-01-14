package ro.ase.ae.ui.base.binding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.mready.photon.Injector
import javax.inject.Singleton

@Singleton
class ViewModelFactory(private val injector: Injector) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return injector.instance(modelClass)
    }
}