package ro.ase.ae.ui.base.binding

import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType


object ViewModelTypeResolver {

    private val cache = mutableMapOf<Class<*>, Class<out ViewModel>>()

    fun findViewModelType(cls: Class<*>): Class<out ViewModel>? {
        if (cache.containsKey(cls)) {
            return cache[cls]
        }

        val viewModelClass = checkParameterizedType(cls)

        if (viewModelClass != null) {
            cache[cls] = viewModelClass
        }

        return viewModelClass
    }

    private fun checkParameterizedType(cls: Class<*>): Class<out ViewModel>? {
        var parameterizedType: ParameterizedType? = null

        if (cls.genericSuperclass is ParameterizedType) {
            parameterizedType = cls.genericSuperclass as ParameterizedType
        }

        if (parameterizedType == null) {
            return null
        }

        parameterizedType.actualTypeArguments
            .filter {
                it is Class<*>
                        && it !== ViewModel::class.java
                        && ViewModel::class.java.isAssignableFrom(it)
            }.forEach {
                @Suppress("UNCHECKED_CAST")
                return it as Class<out ViewModel>
            }

        return null
    }
}