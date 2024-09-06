package com.example.aplikasidicodingevent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "favorite_event")
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var mediaCover: String? = null,
)