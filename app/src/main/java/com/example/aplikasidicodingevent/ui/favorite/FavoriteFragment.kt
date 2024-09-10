package com.example.aplikasidicodingevent.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasidicodingevent.data.FavoriteEventDatabase
import com.example.aplikasidicodingevent.data.response.ListEventsItem
import com.example.aplikasidicodingevent.databinding.FragmentFavoriteBinding
import com.example.aplikasidicodingevent.repository.FavoriteEventRepository
import com.example.aplikasidicodingevent.ui.adapter.ListEventsAdapter


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteEventViewModel
    private lateinit var adapter: ListEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Inisialisasi RecyclerView dan Adapter
        adapter = ListEventsAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        val dao = FavoriteEventDatabase.getDatabase(requireContext()).favoriteEventDao()
        val repository = FavoriteEventRepository(dao)
        val viewModelFactory = FavoriteEventViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteEventViewModel::class.java]

        // Observasi data favorite dan submit ke adapter
        viewModel.getFavoriteEvents().observe(viewLifecycleOwner) { favorites ->
            val items = arrayListOf<ListEventsItem>()
            favorites.map {
                val item = ListEventsItem(id = it.id.toInt(), name = it.name, imageLogo = it.mediaCover)
                items.add(item)
            }
            adapter.submitList(items)

            // Sembunyikan ProgressBar saat data sudah di-load
            binding.progressBar.visibility = View.GONE
        }
    }



}