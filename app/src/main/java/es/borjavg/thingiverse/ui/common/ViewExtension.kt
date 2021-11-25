package es.borjavg.thingiverse.ui.common

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun View.switchVisibility(visible: Boolean) {
    if (visible) visible()
    else gone()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Fragment.toast(@StringRes text: Int) =
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()