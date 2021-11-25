package es.borjavg.thingiverse.features.main.ui.models

import es.borjavg.domain.models.Thing

data class ThingModel(
    val id: String,
    val thumbUrl: String,
    val name: String,
    val commentCount: String,
    val clickable: Boolean,
    val detailUrl: String,
    val liked: Boolean
)

fun Thing.toPresentation(liked: Boolean) = ThingModel(
    thumbUrl = thumb,
    name = name,
    clickable = publicUrl.isNullOrBlank().not(),
    id = id,
    detailUrl = publicUrl.orEmpty(),
    commentCount = "💬 $commentCount",
    liked = liked
)
