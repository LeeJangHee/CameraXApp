package com.example.cameraxapp.ui.fragment

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.R
import com.example.cameraxapp.adapters.AlbumAdapter
import com.example.cameraxapp.databinding.FragmentAlbumBinding
import com.example.cameraxapp.util.AlbumSelectItemDecoration
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    var _binding: FragmentAlbumBinding? = null
    val binding get() = _binding!!
    private val pictureViewModel: PictureViewModel by activityViewModels()
    private val albumAdapter by lazy { AlbumAdapter(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customDecoration = AlbumSelectItemDecoration(requireContext())
        binding.apply {
            viewModel = pictureViewModel
            lifecycleOwner = this@AlbumFragment
            albumRecyclerView.adapter = albumAdapter
            albumRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
            albumRecyclerView.addItemDecoration(customDecoration)
        }

        lifecycleScope.launch {
            pictureViewModel.getAllPictureData().observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    albumAdapter.setData(it)
                }
            }
        }
    }


}