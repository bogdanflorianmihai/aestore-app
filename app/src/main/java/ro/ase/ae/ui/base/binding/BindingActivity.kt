package ro.ase.ae.ui.base.binding

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import ro.ase.ae.BR
import ro.ase.ae.ui.injector

abstract class BindingActivity<T : ViewDataBinding, VM : BindingViewModel> : AppCompatActivity(),
    NotifyPropertyChange {

    private val propertyChangeRegistry = PropertyChangeRegistry()
    private lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var binding: T
        private set
    protected lateinit var viewModel: VM
        private set

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = injector().instance(ViewModelFactory::class.java)
        val viewModelType = ViewModelTypeResolver.findViewModelType(javaClass)

        if (viewModelType != null) {
            @Suppress("UNCHECKED_CAST")
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType) as VM
        }

        viewModel.addOnPropertyChangedCallback(viewModelPropertyChangeCallback)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.host, this)
        binding.setVariable(BR.viewModel, viewModel)

    }

    override fun onDestroy() {
        viewModel.removeOnPropertyChangedCallback(viewModelPropertyChangeCallback)

        super.onDestroy()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

    override fun notifyPropertyChanged(propertyId: Int) {
        propertyChangeRegistry.notifyChange(this, propertyId)
    }

    @Suppress("UNUSED_PARAMETER")
    protected open fun onViewModelPropertyChanged(propertyId: Int) {

    }

    private val viewModelPropertyChangeCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            if (sender == viewModel) {
                onViewModelPropertyChanged(propertyId)
            }
        }
    }

}