package com.example.cameraxapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cameraxapp.R
import com.example.cameraxapp.databinding.FragmentPictureBinding
import com.example.cameraxapp.ui.activity.MainActivity
import com.example.cameraxapp.util.Constants.Companion.TAG
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PictureFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    var _binding: FragmentPictureBinding? = null
    val binding get() = _binding!!

    private val pictureViewModel: PictureViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = MainActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = pictureViewModel
            index = 0
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            openCameraFragment()
        }

        binding.ivPicture.setOnClickListener {
            openCameraFragment()
        }


    }

    private fun openCameraFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, CameraFragment()).commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "Fragment: $requestCode, $resultCode, $data")
    }
}