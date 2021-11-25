package es.borjavg.data.api.model

import com.google.gson.annotations.SerializedName
import es.borjavg.domain.models.Thing

data class ThingData(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("public_url") val publicUrl: String?,
    @SerializedName("comment_count") val commentCount: Int?,
    @SerializedName("thumbnail") val thumbnail: String?,
)

fun ThingData.toDomain() = Thing(
    id = id.orEmpty(),
    thumb = thumbnail.orEmpty(),
    name = name.orEmpty(),
    commentCount = commentCount ?: 0,
    publicUrl = publicUrl
)