package es.borjavg.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ThingEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "thumb") val thumb: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "commentCount") val commentCount: Int?,
    @ColumnInfo(name = "publicUrl") val publicUrl: String?
)