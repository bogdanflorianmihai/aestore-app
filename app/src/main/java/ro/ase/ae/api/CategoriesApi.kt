package ro.ase.ae.api

import io.reactivex.Single
import retrofit2.http.GET
import ro.ase.ae.models.Category

interface CategoriesApi {

    @GET("categories")
    fun getCategories(): Single<List<Category>>
}