package ro.ase.ae.ui.products.details

import ro.ase.ae.models.Review

data class ReviewViewModel(private val review: Review) {

    val name = review.name
    val message = review.message
    val score = review.score
}