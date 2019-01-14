package ro.ase.ae.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import ro.ase.ae.BR
import ro.ase.ae.R
import ro.ase.ae.databinding.FragmentHomeBinding
import ro.ase.ae.ui.base.BaseFragment
import ro.ase.ae.ui.base.BindingRecyclerAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutId = R.layout.fragment_home

    private lateinit var recyclerAdapter: BindingRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = BindingRecyclerAdapter {
            return@BindingRecyclerAdapter R.layout.item_category
        }

        recyclerAdapter.setItemClickedListener {
            if(it is CategoryViewModel) {
                val action = HomeFragmentDirections.actionHomeFragmentToProductsFragment(it.name, it.id)

                findNavController().navigate(action)
            }
        }

        binding.recycler.adapter = recyclerAdapter

        if (viewModel.categories.isNotEmpty()) {
            recyclerAdapter.items = viewModel.categories
        }
    }

    override fun onViewModelPropertyChanged(propertyId: Int) {
        super.onViewModelPropertyChanged(propertyId)

        if (propertyId == BR.categories) {
            recyclerAdapter.items = viewModel.categories
        }
    }
}