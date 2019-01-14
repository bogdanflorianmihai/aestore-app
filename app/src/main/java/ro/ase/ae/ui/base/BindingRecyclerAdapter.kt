package ro.ase.ae.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ro.ase.ae.BR

class BindingRecyclerAdapter(private val layoutSelector: (item: Any?) -> Int) :
    RecyclerView.Adapter<BindingRecyclerAdapter.BindingViewHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var delegate: Any? = null
    private var itemClickedListener: ((item: Any?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BindingViewHolder(DataBindingUtil.bind(view)!!)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return layoutSelector(items[position])
    }

    fun setItemClickedListener(itemClickedListener: (item: Any?) -> Unit) {
        this.itemClickedListener = itemClickedListener
    }

    inner class BindingViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var item: Any? = null

        fun bind(item: Any) {
            if (this.item != item) {
                this.item = item

                binding.setVariable(BR.item, item)

                if (!binding.setVariable(
                        BR.onClick,
                        this
                    ) && !binding.root.hasOnClickListeners()
                ) {
                    binding.root.setOnClickListener(this)
                }

                binding.setVariable(BR.delegate, delegate)

            }
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            itemClickedListener?.invoke(item)
        }
    }
}

