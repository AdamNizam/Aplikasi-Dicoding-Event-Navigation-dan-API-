package com.example.aplikasidicodingevent.ui.detailevent

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.aplikasidicodingevent.data.FavoriteEvent
import com.example.aplikasidicodingevent.databinding.ActivityDetailEventBinding
import com.google.android.material.snackbar.Snackbar
import com.example.aplikasidicodingevent.R

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val viewModel: DetailEventViewModel by viewModels()
    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getIntExtra(EXTRA_EVENT_ID, 8722)

        if (eventId == -1) {
            Log.e("DetailEventActivity", "Invalid Event ID")
            return
        }
        Log.d("DetailEventActivity", "Event ID: $eventId")

        viewModel.getDetailEvent(eventId)

        // Observe LiveData
        viewModel.event.observe(this) { event ->
            Log.d("DetailEventActivity", "Event data: $event")
            event?.let { eventData ->
                binding.tvEventName.text = eventData.name
                binding.tvOwnerName.text = eventData.ownerName
                binding.tvDescription.text = HtmlCompat.fromHtml(
                    eventData.description ?: "",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

                Glide.with(this)
                    .load(eventData.mediaCover)
                    .into(binding.imgCover)

                binding.btnLink.setOnClickListener {
                    val url = eventData.link ?: ""
                    if (url.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    } else {
                        Log.e("DetailEventActivity", "Link is empty")
                    }
                }

                viewModel.isFavorite(eventData.id.toString()).observe(this) { favoriteEvent ->
                    if (favoriteEvent == null) {
                        binding.ivBookmark.setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        binding.ivBookmark.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
                // Handle bookmark button click
                binding.ivBookmark.setOnClickListener {
                    val favoriteEvent = eventData.name?.let { it1 ->
                        FavoriteEvent(
                            id = eventData.id.toString(),
                            name = it1,
                            mediaCover = eventData.mediaCover
                        )
                    }
                    if (favoriteEvent != null) {
                        viewModel.toggleFavorite(favoriteEvent)
                    }
                }

            } ?: run {
                Log.e("DetailEventActivity", "Event data is null")
                showError("Event details not found")
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}
