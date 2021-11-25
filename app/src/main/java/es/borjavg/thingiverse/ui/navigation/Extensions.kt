package es.borjavg.thingiverse.ui.navigation

import android.content.Context

val Context.navigator: Navigator?
    get() = (this as? NavigatorOwner)?.navigator
