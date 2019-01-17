package ro.ase.ae.services

import io.reactivex.Observable
import ro.ase.ae.api.CategoriesApi
import ro.ase.ae.api.ProductsApi
import ro.ase.ae.models.Review
import ro.ase.ae.ui.util.ioSubscribe
import javax.inject.Singleton

@Singleton
class StoreService(
    private val categoriesApi: CategoriesApi,
    private val productsApi: ProductsApi
) {

    fun getCategories() = categoriesApi.getCategories()
        .ioSubscribe()
        .flatMapObservable { Observable.fromIterable(it) }

    fun getProducts(categoryId: Long) = productsApi.getProducts(categoryId)
        .ioSubscribe()
        .flatMapObservable { Observable.fromIterable(it) }

    fun getProduct(productId: Long) = productsApi.getProduct(productId)
        .ioSubscribe()

    fun addReview(review: Review) = productsApi.addReview(review)
        .ioSubscribe()

}