package es.borjavg.thingiverse.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String) = runCatching {
    startActivity(Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    })
}
