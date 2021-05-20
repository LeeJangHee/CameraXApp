package com.example.cameraxapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cameraxapp.adapters.AlbumAdapter
import com.example.cameraxapp.databinding.FragmentAlbumBinding
import com.example.cameraxapp.util.AlbumSelectItemDecoration
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    var _binding: FragmentAlbumBinding? = null
    val binding get() = _binding!!
    private val pictureViewModel: PictureViewModel by activityViewModels()
    private val albumAdapter by lazy { AlbumAdapter(requireActivity(), pictureViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        binding.viewModel = pictureViewModel
        binding.lifecycleOwner = this

        setupRecyclerView()

        lifecycleScope.launch {
            pictureViewModel.getAllPictureData().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    albumAdapter.setData(it)
                }
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val customDecoration = AlbumSelectItemDecoration(requireContext())
        binding.albumRecyclerView.adapter = albumAdapter
        binding.albumRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.albumRecyclerView.addItemDecoration(customDecoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}