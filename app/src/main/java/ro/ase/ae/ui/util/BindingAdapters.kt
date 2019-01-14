package ro.ase.ae.ui.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey


object BindingAdapters {

    @BindingAdapter("visible")
    @JvmStatic
    fun setVisible(view: View, visible: Boolean?) {
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    }

    @BindingAdapter("invisible")
    @JvmStatic
    fun setInvisible(view: View, invisible: Boolean?) {
        view.visibility = if (invisible == true) View.INVISIBLE else View.VISIBLE
    }

    @BindingAdapter("htmlText")
    @JvmStatic
    fun setHtmlText(textView: TextView, text: String?) {
        textView.text = HtmlCompat.fromHtml(text.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?) {
        setImageUrlGlide(view, url, null)
    }

    @BindingAdapter("imageUrl", "placeholder")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?, placeholder: Drawable) {
        setImageUrlGlide(view, url, placeholder)
    }

    @BindingAdapter("imageUrl", "placeholder", "cache")
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?, placeholder: Drawable, cache: Boolean) {
        if (cache) {
            setImageUrlGlide(view, url, placeholder)
        } else {
            setCachelessImageUrlGlide(
                view,
                url,
                placeholder
            )
        }
    }

    private fun setImageUrlGlide(view: ImageView, url: String?, placeholder: Drawable?) {
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.placeholderOf(placeholder))
            .into(view)
    }

    private fun setCachelessImageUrlGlide(view: ImageView, url: String?, placeholder: Drawable) {
        Glide.with(view.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(placeholder)
                    .signature(ObjectKey(System.currentTimeMillis().toString()))
            )
            .into(view)
    }
}