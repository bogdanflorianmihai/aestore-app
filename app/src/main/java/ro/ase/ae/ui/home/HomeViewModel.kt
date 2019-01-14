package ro.ase.ae.ui.home

import androidx.databinding.Bindable
import ro.ase.ae.BR
import ro.ase.ae.services.StoreService
import ro.ase.ae.ui.base.BaseViewModel
import ro.ase.ae.ui.base.LOADING
import ro.ase.ae.ui.base.READY
import ro.ase.ae.ui.util.mainThread

class HomeViewModel(private val storeService: StoreService) : BaseViewModel() {

    @get:Bindable
    var categories = listOf<CategoryViewModel>()
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        state = LOADING
        disposables += storeService.getCategories()
            .mainThread()
            .map { CategoryViewModel(it) }
            .toList()
            .doFinally { state = READY }
            .subscribe({
                categories = it
                notifyPropertyChanged(BR.categories)
            }, {
                //todo
            })
    }
}