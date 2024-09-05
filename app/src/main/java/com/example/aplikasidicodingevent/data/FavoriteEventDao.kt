package com.example.aplikasidicodingevent.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(favoriteEvent: FavoriteEvent)
}