package es.borjavg.thingiverse.ui.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ImageLoader @Inject constructor(
    @ActivityContext private val context: Context,
) {
    fun load(url: String, into: ImageView) {
        Glide.with(context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(10))
            .into(into)
    }
}