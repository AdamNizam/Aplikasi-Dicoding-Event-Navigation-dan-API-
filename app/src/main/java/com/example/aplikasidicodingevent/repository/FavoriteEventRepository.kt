package com.example.aplikasidicodingevent.repository


import com.example.aplikasidicodingevent.data.FavoriteEvent
import com.example.aplikasidicodingevent.data.FavoriteEventDao

class FavoriteEventRepository ( private var favoriteEventDao: FavoriteEventDao) {
    suspend fun addFavoriteEvent( favoriteEvent: FavoriteEvent){
        favoriteEventDao.insertFavoriteEvent(favoriteEvent)
    }
}