package ro.ase.ae.ui.products

import ro.ase.ae.models.Product
import ro.ase.ae.ui.products.details.ReviewViewModel

data class ProductViewModel(val product: Product) {

    val id = product.id
    val name = product.name
    val description = product.description
    val image = product.image
    val category = product.category
    val price = "${product.price} lei"
    val reviews = product.reviews.map { ReviewViewModel(it) }
    val hasReviews = product.reviews.isNotEmpty()

    val score = if (hasReviews) {
        reviews.sumBy { it.score } / reviews.size
    } else {
        0
    }
}