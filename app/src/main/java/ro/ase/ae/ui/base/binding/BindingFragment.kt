package ro.ase.ae.ui.base.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ro.ase.ae.BR
import ro.ase.ae.ui.base.binding.ViewModelTypeResolver.findViewModelType
import ro.ase.ae.ui.injector

abstract class BindingFragment<T : ViewDataBinding, VM : BindingViewModel> : Fragment(),
    NotifyPropertyChange {

    private val propertyChangeRegistry = PropertyChangeRegistry()

    protected lateinit var binding: T
        private set
    protected lateinit var viewModel: VM
        private set

    private lateinit var viewModelFactory: ViewModelFactory

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = context!!.injector().instance(ViewModelFactory::class.java)

        val viewModelType = findViewModelType(javaClass)

        if (viewModelType != null) {
            @Suppress("UNCHECKED_CAST")
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType) as VM
        }
        viewModel.addOnPropertyChangedCallback(viewModelPropertyChangeCallback)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        binding.setVariable(BR.host, this)
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onDestroyView() {
        viewModel.removeOnPropertyChangedCallback(viewModelPropertyChangeCallback)
        super.onDestroyView()
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