package com.example.cameraxapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameraxapp.model.PictureModel
import kotlinx.coroutines.launch

class PictureViewModel : ViewModel() {
    private var _pictureLiveData: MutableLiveData<ArrayList<PictureModel>> = MutableLiveData()
    private val pictureLiveData get() = _pictureLiveData

    fun setTakePhoto(picture: ArrayList<PictureModel>) {
        _pictureLiveData.value = picture
    }

    fun setPictureData(newPictureList: ArrayList<PictureModel>) {
        _pictureLiveData.value = newPictureList
    }

    fun getAllPictureData(): LiveData<ArrayList<PictureModel>> {
        return pictureLiveData
    }

    fun getPictureData(index: Int): PictureModel? {
        return pictureLiveData.value?.get(index)
    }

}