package ro.ase.ae.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ro.ase.ae.ui.base.binding.BindingActivity

abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : BindingActivity<T, VM>(),
    BaseViewModel.EventListener, CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.listeners += this
    }

    override fun onDestroy() {
        viewModel.listeners -= this
        coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onViewModelEvent(what: Int, payload: Any?) = Unit

    override fun onBackPressed() {
        val handled = (supportFragmentManager.fragments
                + supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments.orEmpty())
            .filterIsInstance<BaseFragment<*, *>>()
            .any { it.onBackPressed() }

        if (!handled) {
            super.onBackPressed()
        }
    }

}