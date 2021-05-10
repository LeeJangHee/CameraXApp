package com.example.cameraxapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.cameraxapp.model.PictureModel

class AlbumDiffUtil(
    private val oldList: ArrayList<PictureModel?>,
    private val newList: ArrayList<PictureModel?>
) :DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 메모리 주소 비교
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 아이템 비교
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}