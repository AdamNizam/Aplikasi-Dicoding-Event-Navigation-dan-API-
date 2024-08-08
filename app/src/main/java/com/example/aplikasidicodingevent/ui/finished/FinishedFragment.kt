package com.example.aplikasidicodingevent.ui.finished

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasidicodingevent.R
import com.example.aplikasidicodingevent.data.response.ListEventsResponse
import com.example.aplikasidicodingevent.data.retrofit.ApiConfig
import com.example.aplikasidicodingevent.databinding.FragmentFinishedBinding
import com.example.aplikasidicodingevent.databinding.FragmentUpComingBinding
import com.example.aplikasidicodingevent.ui.adapter.ListEventsAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ListEventsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListEventsAdapter()
        binding.rvHeroes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHeroes.adapter = adapter
        fetchEvents()
    }

    private fun fetchEvents() {
        if (_binding == null) {
            Log.e("FinishedFragment", "Binding is null")
        } else {
            showLoading(true)
            val apiService = ApiConfig.getApiService()
            val call = apiService.getEvents(0)
            call.enqueue(object : Callback<ListEventsResponse> {
                override fun onResponse(
                    call: Call<ListEventsResponse>,
                    response: Response<ListEventsResponse>
                ) {
                    if (_binding != null) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            Log.d("FinishedFragment", "Data received: ${response.body()}")
                            val events = response.body()?.listEvents
                            events?.let {
                                adapter.submitList(it)
                            } ?: run {
                                showError("Data kosong")
                            }
                        } else {
                            showError("Gagal mengambil data: ${response.message()}")
                        }
                    }
                }

                override fun onFailure(call: Call<ListEventsResponse>, t: Throwable) {
                    if (_binding != null) {
                        showLoading(false)
                        showError("Gagal terhubung ke server: ${t.message}")
                    }
                    Log.e("FinishedFragment", "Error fetching events", t)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        _binding?.let {
            it.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}