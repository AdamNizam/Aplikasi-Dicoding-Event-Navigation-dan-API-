package com.example.aplikasidicodingevent.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasidicodingevent.data.FavoriteEvent
import com.example.aplikasidicodingevent.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventViewModel(private val repository: FavoriteEventRepository) : ViewModel() {

}