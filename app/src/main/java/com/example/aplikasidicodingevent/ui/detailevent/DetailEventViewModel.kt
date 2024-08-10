package com.example.aplikasidicodingevent.ui.detailevent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasidicodingevent.data.response.BaseResponse
import com.example.aplikasidicodingevent.data.response.DetailEventResponse
import com.example.aplikasidicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {

    private val _event = MutableLiveData<DetailEventResponse?>()
    val event: LiveData<DetailEventResponse?> = _event

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
