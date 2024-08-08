package com.example.aplikasidicodingevent

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasidicodingevent.data.response.ListEventsItem
import com.example.aplikasidicodingevent.data.response.ListEventsResponse
import com.example.aplikasidicodingevent.data.retrofit.ApiConfig
import com.example.aplikasidicodingevent.databinding.ActivityMainBinding
import com.example.aplikasidicodingevent.ui.adapter.ListEventsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val ListEvents_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_upcoming,
                R.id.navigation_finished
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//
//        supportActionBar?.hide()
//
//        val layoutManager = LinearLayoutManager(this)
//        binding.rvHeroes.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvHeroes.addItemDecoration(itemDecoration)
//
//        findEvents()

    }
//
//    private fun findEvents() {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getEvents(ListEvents_ID)
//        client.enqueue(object : Callback<ListEventsResponse> {
//            override fun onResponse(
//                call: Call<ListEventsResponse>,
//                response: Response<ListEventsResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setEventsData(responseBody.listEvents)
////                        setEventsData(responseBody.restaurant.customerReviews)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<ListEventsResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }
//    private fun setEventsData(listEvents: List<ListEventsItem>){
//        val adapter = ListEventsAdapter()
//        binding.rvHeroes.adapter = adapter
//        adapter.submitList(listEvents)
//    }
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}