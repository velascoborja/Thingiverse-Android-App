package es.borjavg.data.db.model

import es.borjavg.domain.models.Thing

fun ThingEntity.toDomain() = Thing(
    id = id,
    thumb = thumb.orEmpty(),
    name = name.orEmpty(),
    likeCount = likeCount ?: 0,
    publicUrl = publicUrl
)

fun Thing.toEntity() = ThingEntity(
    id = id,
    thumb = thumb,
    name = name,
    likeCount = likeCount,
    publicUrl = publicUrl
)