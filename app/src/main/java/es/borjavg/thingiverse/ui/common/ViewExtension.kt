package es.borjavg.thingiverse.ui.common

import android.view.View

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