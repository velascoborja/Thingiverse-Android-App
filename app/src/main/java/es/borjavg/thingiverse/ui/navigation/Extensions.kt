package es.borjavg.thingiverse.ui.navigation

import android.content.Context
import androidx.fragment.app.Fragment

val Context.navigator: Navigator?
    get() = (this as? NavigatorOwner)?.navigator

val Fragment.navigator: Navigator?
    get() = context?.navigator
