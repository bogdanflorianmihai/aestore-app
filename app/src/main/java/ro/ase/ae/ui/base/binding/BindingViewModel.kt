package ro.ase.ae.ui.base.binding

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

abstract class BindingViewModel : ViewModel(), NotifyPropertyChange {

    private val propertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

    override fun notifyPropertyChanged(propertyId: Int) {
        propertyChangeRegistry.notifyChange(this, propertyId)
    }
}