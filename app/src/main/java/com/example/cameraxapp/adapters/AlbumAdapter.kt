package com.example.cameraxapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.databinding.ItemAlbumBinding
import com.example.cameraxapp.model.PictureModel

const val TAG = "janghee"

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var pictureList = List<PictureModel?>(8) { null } as ArrayList<PictureModel?>
    private var buttonViewList = ArrayList<Boolean>()
    private var selectItemIndex = -1 // -1 : not selected  / 0: first item


    inner class AlbumViewHolder(
        val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(pictureModel: PictureModel, isBool: Boolean) {
            with(binding) {
                result = pictureModel
                executePendingBindings()
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlbumBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        buttonViewList.add(false)

        // 버튼 visible / gone
        holder.binding.bool = buttonViewList[position]

        // 리사이클러 아이템 클릭
        holder.itemView.setOnClickListener {
            val preIndex = selectItemIndex
            selectItemIndex = position

            if (preIndex == -1) {
                buttonViewList[position] = !buttonViewList[position]
                notifyItemChanged(position)
            } else {
                if (preIndex == position) {
                    buttonViewList[position] = !buttonViewList[position]
                    notifyItemChanged(position)
                    selectItemIndex = -1
                } else {
                    buttonViewList[position] = !buttonViewList[position]
                    buttonViewList[preIndex] = !buttonViewList[preIndex]

                    notifyItemChanged(preIndex)
                    notifyItemChanged(position)
                }
            }
        }
        pictureList[position]?.let { holder.bind(it, buttonViewList[position]) }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun setData(newList: ArrayList<PictureModel?>) {
        pictureList = newList
        notifyDataSetChanged()
    }
}