package ro.ase.ae.ui.products.details

import androidx.databinding.Bindable
import ro.ase.ae.BR
import ro.ase.ae.models.Review
import ro.ase.ae.services.StoreService
import ro.ase.ae.ui.base.BaseViewModel
import ro.ase.ae.ui.base.LOADING
import ro.ase.ae.ui.base.READY
import ro.ase.ae.ui.products.ProductViewModel
import ro.ase.ae.ui.util.mainThread

class ProductDetailsViewModel(private val storeService: StoreService) : BaseViewModel() {

    @get:Bindable
    var items = listOf<Any>()

    fun loadProduct(productId: Long) {
        state = LOADING
        disposables += storeService.getProduct(productId)
            .mainThread()
            .doFinally { state = READY }
            .subscribe({
                val product = ProductViewModel(it)
                val items = mutableListOf<Any>()
                items.add(product)
                items.addAll(product.reviews)

                this@ProductDetailsViewModel.items = items
                notifyPropertyChanged(BR.items)
            }, {
                //todo
            })
    }

    fun addReview(productId: Long, name: String, message: String, rating: Int) {
        disposables += storeService.addReview(Review(productId, name, message, rating))
            .mainThread()
            .subscribe({
                loadProduct(productId)
            }, {
                //todo
            })
    }
}