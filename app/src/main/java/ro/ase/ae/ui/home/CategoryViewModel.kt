package ro.ase.ae.ui.home

import ro.ase.ae.models.Category

data class CategoryViewModel(val category: Category) {

    val id = category.id
    val name = category.name
    val description = category.description
}