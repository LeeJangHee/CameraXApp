package com.example.cameraxapp.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.adapters.AlbumAdapter
import com.example.cameraxapp.databinding.FragmentAlbumBinding
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    var _binding: FragmentAlbumBinding? = null
    val binding get() = _binding!!
    private val pictureViewModel: PictureViewModel by activityViewModels()
    private val albumAdapter by lazy { AlbumAdapter() }

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
        binding.apply {
            viewModel = pictureViewModel
            lifecycleOwner = viewLifecycleOwner
            albumRecyclerView.adapter = albumAdapter
            albumRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4).apply {
//                reverseLayout = true // RTL 맞춰서 오른쪽으로 쌓임
//                stackFromEnd = true // 영역 끝부분 부터 추가
            }
            albumRecyclerView.addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val position = parent.getChildAdapterPosition(view)
                    outRect.top = -100
                }
            })
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