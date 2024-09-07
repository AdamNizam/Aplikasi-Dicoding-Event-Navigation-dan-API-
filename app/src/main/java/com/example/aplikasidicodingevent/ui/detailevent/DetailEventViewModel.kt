package com.example.aplikasidicodingevent.ui.detailevent

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasidicodingevent.data.FavoriteEvent
import com.example.aplikasidicodingevent.data.FavoriteEventDao
import com.example.aplikasidicodingevent.data.FavoriteEventDatabase
import com.example.aplikasidicodingevent.data.response.BaseResponse
import com.example.aplikasidicodingevent.data.response.DetailEventResponse
import com.example.aplikasidicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel (application: Application) : AndroidViewModel(application) {

    private val favoriteEventDao: FavoriteEventDao =
        FavoriteEventDatabase.getDatabase(application).favoriteEventDao()

    private val _event = MutableLiveData<DetailEventResponse?>()
    val event: LiveData<DetailEventResponse?> = _event

    // Mendapatkan LiveData yang memeriksa apakah event adalah favorite
    fun isFavorite(eventId: String): LiveData<FavoriteEvent?> {
        return favoriteEventDao.getFavoriteEventById(eventId)
    }

    // Menyimpan atau menghapus event dari daftar favorite
    fun toggleFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            val existingEvent = favoriteEventDao.getFavoriteEventById(event.id).value
            if (existingEvent == null) {
                // Insert event jika tidak ada
                favoriteEventDao.insertFavoriteEvent(event)
            } else {
                // Delete event jika sudah ada
                favoriteEventDao.deleteFavoriteEventById(event.toString() )
            }
        }
    }

    // Ubah ID parameter menjadi Int
    fun getDetailEvent(id: Int) {
        Log.d("DetailEventViewModel", "Fetching details for ID: $id")

        val idString = id.toString()
        val client = ApiConfig.getApiService().getDetailEvent(idString)

        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                if (response.isSuccessful) {
                    _event.value = response.body()?.event
                    Log.d("DetailEventViewModel", "Data received: ${response.body()}")
                } else {
                    _event.value = null
                    Log.e("DetailEventViewModel", "Error response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _event.value = null
                Log.e("DetailEventViewModel", "Failure: ${t.message}")
            }
        })
    }
}
