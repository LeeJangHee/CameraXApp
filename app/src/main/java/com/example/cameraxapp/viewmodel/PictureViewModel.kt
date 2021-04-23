package com.example.cameraxapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cameraxapp.model.PictureModel

class PictureViewModel: ViewModel() {
    private var _pictureLiveData: MutableLiveData<List<PictureModel>> = MutableLiveData()
    private val pictureLiveData get() = _pictureLiveData


    fun setPictureData(newPictureList: List<PictureModel>){
        _pictureLiveData.value = newPictureList
    }

    fun getAllPictureData(): List<PictureModel> {
        return pictureLiveData.value!!
    }

    fun getPictureData(index: Int): PictureModel {
        return pictureLiveData.value!![index]
    }

}