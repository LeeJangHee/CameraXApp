package com.example.cameraxapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cameraxapp.model.PictureModel

class PictureViewModel : ViewModel() {
    private var _pictureLiveData: MutableLiveData<ArrayList<PictureModel?>> = MutableLiveData()
    private val pictureLiveData get() = _pictureLiveData

    private var _buttonViewLiveData: MutableLiveData<List<Boolean>> = MutableLiveData()
    private val buttonViewLiveData get() = _buttonViewLiveData


    fun setTakePhoto(picture: ArrayList<PictureModel?>) {
        _pictureLiveData.value = picture
    }

    fun setPictureData(newPictureList: ArrayList<PictureModel?>) {
        _pictureLiveData.value = newPictureList
    }

    fun getAllPictureData(): LiveData<ArrayList<PictureModel?>> {
        return pictureLiveData
    }

    fun getPictureData(index: Int): PictureModel? {
        return pictureLiveData.value?.get(index)
    }

    fun getPictureArray(): ArrayList<PictureModel?>? {
        return pictureLiveData.value
    }

}