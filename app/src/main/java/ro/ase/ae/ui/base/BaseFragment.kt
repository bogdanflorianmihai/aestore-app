package ro.ase.ae.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ro.ase.ae.ui.base.binding.BindingFragment

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : BindingFragment<T, VM>(),
    BaseViewModel.EventListener, CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listeners += this
    }

    override fun onDestroyView() {
        viewModel.listeners -= this
        coroutineContext.cancel()
        super.onDestroyView()
    }

    override fun onViewModelEvent(what: Int, payload: Any?) = Unit

    open fun onBackPressed(): Boolean {
        return childFragmentManager.fragments.filterIsInstance<BaseFragment<*, *>>()
            .any { it.onBackPressed() }
    }

}