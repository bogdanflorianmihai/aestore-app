package ro.ase.ae.models

import ro.ase.ae.api.Json

class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val image: String?,
    val category: Category,
    val reviews: List<Review>
)

data class Review(
    @Json("product_id") val productId: Long,
    @Json("name") val name: String,
    @Json("content") val message: String,
    @Json("score") val score: Int
)