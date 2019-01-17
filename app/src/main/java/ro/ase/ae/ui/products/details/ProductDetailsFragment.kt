package ro.ase.ae.ui.products.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ro.ase.ae.BR
import ro.ase.ae.R
import ro.ase.ae.databinding.FragmentProductDetailsBinding
import ro.ase.ae.ui.base.BaseFragment
import ro.ase.ae.ui.base.BindingRecyclerAdapter
import ro.ase.ae.ui.products.ProductViewModel
import ro.ase.ae.ui.util.hideKeyboard

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel>() {

    override val layoutId = R.layout.fragment_product_details

    private lateinit var recyclerAdapter: BindingRecyclerAdapter

    @get:Bindable
    var bottomSheetOpen = false
        private set

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var productId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ProductDetailsFragmentArgs.fromBundle(arguments!!)
        notifyPropertyChanged(BR.title)

        productId = args.productId
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

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomSheetOpen = false
                        notifyPropertyChanged(BR.bottomSheetOpen)
                        hideKeyboard(bottomSheet)
                    }
                }
            }

        })

        binding.bottomSheetOverlay.setOnClickListener {
            closeBottomSheet()
        }

        binding.btnAddReview.setOnClickListener {
            openBottomSheet()
        }

        binding.btnSubmitReview.setOnClickListener {
            onSubmitReviewClicked(it)
        }

    }

    override fun onViewModelPropertyChanged(propertyId: Int) {
        super.onViewModelPropertyChanged(propertyId)

        if (propertyId == BR.items) {
            recyclerAdapter.items = viewModel.items
        }
    }

    private fun openBottomSheet() {
        bottomSheetOpen = true
        notifyPropertyChanged(BR.bottomSheetOpen)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight = 800
    }

    private fun closeBottomSheet() {
        bottomSheetOpen = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        notifyPropertyChanged(BR.bottomSheetOpen)
    }

    private fun onSubmitReviewClicked(view: View) {
        val name = binding.etName.text.toString()
        val message = binding.etMessage.text.toString()
        val rating = binding.ratingBar.rating

        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            return
        } else {
            binding.etName.error = null
        }

        if (message.isEmpty()) {
            binding.etName.error = "Message is required"
            return
        } else {
            binding.etMessage.error = null
        }

        if (rating == 0f) {
            Toast.makeText(requireContext(), "Please enter your rating", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard(view)
        productId?.let {
            viewModel.addReview(it, name, message, rating.toInt())
            closeBottomSheet()
            binding.etName.setText("")
            binding.etMessage.setText("")
            binding.ratingBar.rating = 0f
        }

    }
}