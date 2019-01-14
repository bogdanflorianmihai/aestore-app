package ro.ase.ae.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ro.ase.ae.models.Product

interface ProductsApi {

    @GET("categories/{categoryId}/products")
    fun getProducts(@Path("categoryId") categoryId: Long): Single<List<Product>>
}