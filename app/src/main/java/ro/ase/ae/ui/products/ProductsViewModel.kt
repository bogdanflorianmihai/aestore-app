package ro.ase.ae.ui.products

import androidx.databinding.Bindable
import ro.ase.ae.BR
import ro.ase.ae.services.StoreService
import ro.ase.ae.ui.base.BaseViewModel
import ro.ase.ae.ui.base.LOADING
import ro.ase.ae.ui.base.READY
import ro.ase.ae.ui.util.mainThread

class ProductsViewModel(private val storeService: StoreService) : BaseViewModel() {

    @get:Bindable
    var products = listOf<ProductViewModel>()

    fun loadProducts(categoryId: Long) {
        state = LOADING
        disposables += storeService.getProducts(categoryId)
            .mainThread()
            .map { ProductViewModel(it) }
            .toList()
            .doFinally { state = READY }
            .subscribe({
                products = it
                notifyPropertyChanged(BR.products)
            }, {
                //todo
            })
    }
}