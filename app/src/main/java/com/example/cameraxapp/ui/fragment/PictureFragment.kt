package com.example.cameraxapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cameraxapp.R
import com.example.cameraxapp.databinding.FragmentPictureBinding
import com.example.cameraxapp.ui.activity.MainActivity
import com.example.cameraxapp.util.Constants.Companion.TAG


class PictureFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    var _binding: FragmentPictureBinding? = null
    val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = MainActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPicture.setOnClickListener {
            openFragment()
        }

    }

    private fun openFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, CameraFragment()).commit()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "Fragment: $requestCode, $resultCode, $data")
    }
}