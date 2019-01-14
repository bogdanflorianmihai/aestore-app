package ro.ase.ae.ui.base

import androidx.databinding.Bindable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ro.ase.ae.BR
import ro.ase.ae.ui.base.binding.BindingViewModel

const val READY = 0
const val LOADING = 1
const val WORKING = 2

open class BaseViewModel : BindingViewModel(), CoroutineScope {

    interface EventListener {
        fun onViewModelEvent(what: Int, payload: Any?)
    }

    override val coroutineContext = SupervisorJob() + Dispatchers.Main

    protected val disposables = mutableSetOf<Disposable>()
    protected var state: Int = READY
        set(value) {
            val oldValue = field
            field = value

            if (oldValue == LOADING || value == LOADING) {
                notifyPropertyChanged(BR.loading)
            }

            if (oldValue == WORKING || value == WORKING) {
                notifyPropertyChanged(BR.working)
            }
        }

    @get:Bindable
    val loading: Boolean
        get() = state == LOADING

    @get:Bindable
    val working: Boolean
        get() = state == WORKING

    val listeners = mutableSetOf<EventListener>()

    protected fun emit(what: Int, payload: Any? = null) {
        listeners.forEach { it.onViewModelEvent(what, payload) }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        disposables.forEach { it.dispose() }
        super.onCleared()
    }
}