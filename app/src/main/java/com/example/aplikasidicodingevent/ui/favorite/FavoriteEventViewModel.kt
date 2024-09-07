package com.example.aplikasidicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasidicodingevent.data.FavoriteEvent
import com.example.aplikasidicodingevent.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventViewModel (private var repository: FavoriteEventRepository): ViewModel(){
    // Mengambil data favorite dari repository
    fun getFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        return repository.getFavoriteEvent()
    }

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            repository.addFavoriteEvent(favoriteEvent)
        }

    }
}