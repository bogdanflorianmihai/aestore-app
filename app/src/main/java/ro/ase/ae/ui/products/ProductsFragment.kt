package ro.ase.ae.ui.products

import android.os.Bundle
import android.view.View
import androidx.databinding.Bindable
import androidx.navigation.findNavController
import ro.ase.ae.BR
import ro.ase.ae.R
import ro.ase.ae.databinding.FragmentProductsBinding
import ro.ase.ae.ui.base.BaseFragment
import ro.ase.ae.ui.base.BindingRecyclerAdapter

class ProductsFragment : BaseFragment<FragmentProductsBinding, ProductsViewModel>() {

    override val layoutId = R.layout.fragment_products

    @get:Bindable
    var title: String = ""
        private set

    private lateinit var recyclerAdapter: BindingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ProductsFragmentArgs.fromBundle(arguments!!)
        title = args.title
        notifyPropertyChanged(BR.title)

        viewModel.loadProducts(args.categoryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { it.findNavController().popBackStack() }

        recyclerAdapter = BindingRecyclerAdapter {
            return@BindingRecyclerAdapter R.layout.item_product
        }

        recyclerAdapter.setItemClickedListener {
            //todo
        }

        binding.recycler.adapter = recyclerAdapter

        if (viewModel.products.isNotEmpty()) {
            recyclerAdapter.items = viewModel.products
        }

    }

    override fun onViewModelPropertyChanged(propertyId: Int) {
        super.onViewModelPropertyChanged(propertyId)

        if (propertyId == BR.products) {
            recyclerAdapter.items = viewModel.products
        }
    }
}