package com.example.cameraxapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.databinding.ItemAlbumBinding
import com.example.cameraxapp.model.PictureModel
import com.example.cameraxapp.util.AlbumDiffUtil

const val TAG = "janghee"

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var pictureList = List<PictureModel?>(8) { null } as ArrayList<PictureModel?>
    private var buttonView = ArrayList<Boolean>()
    private var selectItemIndex = -1


    inner class AlbumViewHolder(
        val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

//        init {
//            clickListener = AlbumListener {
//                Toast.makeText(binding.root.context, "click $it", Toast.LENGTH_SHORT).show()
//                binding.bool = it
//            }
//        }
//
//        fun bind(pictureModel: PictureModel, isBool: Boolean) {
//            with(binding) {
//                result = pictureModel
//                executePendingBindings()
//            }
//        }

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
        buttonView.add(false)
        val currentPicture = pictureList[position]

//        pictureList[position]?.let { holder.bind(it, buttonView[position]) }

        holder.binding.apply {
            bool = this@AlbumAdapter.buttonView[position]
            click = AlbumListener {
                val preIndex = selectItemIndex
                selectItemIndex = position

                this@AlbumAdapter.buttonView[preIndex] = !this@AlbumAdapter.buttonView[preIndex]
                this@AlbumAdapter.buttonView[position] = !this@AlbumAdapter.buttonView[position]
            }
            result = currentPicture
        }
        holder.itemView.setOnClickListener {
            val preIndex = selectItemIndex
            selectItemIndex = position

            if (preIndex < 0) {
                this@AlbumAdapter.buttonView[position] = !this@AlbumAdapter.buttonView[position]
                notifyItemChanged(position)
            } else {
                if (preIndex == position) {
                    this@AlbumAdapter.buttonView[preIndex] = !this@AlbumAdapter.buttonView[preIndex]
                    notifyItemChanged(preIndex)
                    return@setOnClickListener
                }
                this@AlbumAdapter.buttonView[preIndex] = !this@AlbumAdapter.buttonView[preIndex]
                this@AlbumAdapter.buttonView[position] = !this@AlbumAdapter.buttonView[position]

                notifyItemChanged(preIndex)
                notifyItemChanged(position)
            }

        }
//        holder.itemView.setOnClickListener {
//            val preIndex = selectItemIndex
//            selectItemIndex = position
//
//            buttonView[preIndex] = !buttonView[preIndex]
//            buttonView[position] = !buttonView[position]
//
//            notifyItemChanged(preIndex)
//            notifyItemChanged(position)
//
//            clickListener.onItemClick()
//        }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun setData(newList: ArrayList<PictureModel?>) {
        pictureList = newList
        notifyDataSetChanged()
//        val albumDiffUtil = AlbumDiffUtil(oldList = pictureList, newList = newList)
//        val diffUtilResult = DiffUtil.calculateDiff(albumDiffUtil)
//        pictureList = newList
//        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun interface AlbumListener {
        fun onItemClick(isVisible: Boolean)
    }
}