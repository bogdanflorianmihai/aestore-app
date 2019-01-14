package ro.ase.ae.ui.base.binding

import androidx.databinding.Observable

interface NotifyPropertyChange : Observable {
    fun notifyPropertyChanged(propertyId: Int)
}