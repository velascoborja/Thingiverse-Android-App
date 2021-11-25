package es.borjavg.domain.models

data class Thing(
    val id: String,
    val thumb: String,
    val name: String,
    val commentCount: Int,
    val publicUrl: String?
)