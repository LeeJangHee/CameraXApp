package com.example.cameraxapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.databinding.FragmentAlbumBinding
import com.example.cameraxapp.databinding.ItemAlbumBinding
import com.example.cameraxapp.model.PictureModel
import com.example.cameraxapp.util.AlbumDiffUtil

class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    private var pictureList = List<PictureModel?>(8){ null } as ArrayList<PictureModel?>
    val pre_index = -1


    class AlbumViewHolder(private val binding: ItemAlbumBinding):
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.albumImageView.setOnClickListener {
                binding.buttonView.visibility = View.VISIBLE
//                return@setOnLongClickListener true
            }
        }

        fun bind(pictureModel: PictureModel, now_index: Int, pre_index: Int) {
            with(binding) {
                preIndex = pre_index

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
        pictureList[position]?.let { holder.bind(it, position, pre_index) }
//        holder.itemView.setOnClickListener {
//            Toast.makeText(holder.itemView.context, "test $position", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun setData(newList: ArrayList<PictureModel?>) {
        val albumDiffUtil = AlbumDiffUtil(oldList = pictureList, newList = newList)
        val diffUtilResult = DiffUtil.calculateDiff(albumDiffUtil)
        pictureList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}