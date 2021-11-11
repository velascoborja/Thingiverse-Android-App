@file:Suppress("FunctionName")

package es.borjavg.thingiverse.ui.common

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Default implementation of ItemCallback that checks for item equality by checking their
 * idSelector. If no idSelector is provided, the class will simply compare one item to another
 */
class DefaultItemCallback<T>(
    private val idSelector: ((T) -> Any?)? = null,
    private val changePayload: (T, T) -> Any? = { _, _ -> null }
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return idSelector?.let { return it(oldItem) == it(newItem) } ?: oldItem == newItem
    }

    /**
     * Note that equal checking on data classes compares field by field but in other classes
     * typically you'll implement [Any.equals] and [Any.hashCode], and use it to compare object
     * contents.
     */
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: T, newItem: T): Any? = changePayload(oldItem, newItem)
}

fun <T> EmptyChangedPayload(): (T, T) -> Any? = { _, _ -> Unit }
