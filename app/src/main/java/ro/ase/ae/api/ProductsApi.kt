package ro.ase.ae.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ro.ase.ae.models.Product
import ro.ase.ae.models.Review

interface ProductsApi {

    @GET("categories/{categoryId}/products")
    fun getProducts(@Path("categoryId") categoryId: Long): Single<List<Product>>

    @GET("products/{productId}")
    fun getProduct(@Path("productId") productId: Long): Single<Product>

    @POST("reviews")
    fun addReview(@Body review: Review): Single<Any>
}