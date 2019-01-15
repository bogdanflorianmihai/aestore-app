package ro.ase.ae.ui.products.details

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import ro.ase.ae.BR
import ro.ase.ae.R
import ro.ase.ae.databinding.FragmentProductDetailsBinding
import ro.ase.ae.ui.base.BaseFragment
import ro.ase.ae.ui.base.BindingRecyclerAdapter
import ro.ase.ae.ui.products.ProductViewModel

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

    override val layoutId = R.layout.fragment_product_details

    private lateinit var recyclerAdapter: BindingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ProductDetailsFragmentArgs.fromBundle(arguments!!)
        notifyPropertyChanged(BR.title)

        viewModel.loadProduct(args.productId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { it.findNavController().popBackStack() }

        recyclerAdapter = BindingRecyclerAdapter {
            return@BindingRecyclerAdapter when (it) {
                is ProductViewModel -> R.layout.item_product_header
                is ReviewViewModel -> R.layout.item_product_review
                else -> throw IllegalStateException()
            }
        }

        binding.recycler.adapter = recyclerAdapter

        if (viewModel.items.isNotEmpty()) {
            recyclerAdapter.items = viewModel.items
        }

    }

    override fun onViewModelPropertyChanged(propertyId: Int) {
        super.onViewModelPropertyChanged(propertyId)

        if (propertyId == BR.items) {
            recyclerAdapter.items = viewModel.items
        }
    }
}