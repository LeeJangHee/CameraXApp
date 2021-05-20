package com.example.cameraxapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxapp.R
import com.example.cameraxapp.databinding.ItemAlbumBinding
import com.example.cameraxapp.model.PictureModel
import com.example.cameraxapp.ui.fragment.CameraFragment
import com.example.cameraxapp.util.Constants.Companion.ALBUM_FRAGMENT
import com.example.cameraxapp.util.Constants.Companion.buttonViewList
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.item_album.view.*

const val TAG = "janghee"

class AlbumAdapter(
    private val requireActivity: FragmentActivity,
    private val pictureViewModel: PictureViewModel
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var pictureList = List<PictureModel?>(8) { null } as ArrayList<PictureModel?>
//    private var buttonViewList = ArrayList<Boolean>()
    private var selectItemIndex = -1 // -1 : not selected  / 0: first item


    inner class AlbumViewHolder(
        val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(pictureModel: PictureModel) {
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
//        buttonViewList.add(false)

        // 버튼 visible / gone
        holder.binding.bool = buttonViewList[position]

        // 리사이클러 아이템 클릭
        holder.itemView.setOnLongClickListener {
            val preIndex = selectItemIndex
            selectItemIndex = position

            if (preIndex == -1) {
                buttonViewList[position] = !buttonViewList[position]
//                notifyItemChanged(position)
            } else {
                if (preIndex == position) {
                    buttonViewList[position] = !buttonViewList[position]
//                    notifyItemChanged(position)
                    selectItemIndex = -1
                } else {
                    buttonViewList[position] = !buttonViewList[position]
                    buttonViewList[preIndex] = !buttonViewList[preIndex]

//                    notifyItemChanged(preIndex)
//                    notifyItemChanged(position)
                }
            }
            notifyDataSetChanged()
            true
        }

        holder.binding.albumCamera.setOnClickListener {
            // 카메라 클릭
            selectItemIndex = -1
            val fm = requireActivity.supportFragmentManager
            val transaction = fm.beginTransaction()
            transaction.replace(R.id.fragment, CameraFragment.newInstance(position))
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
            transaction.isAddToBackStackAllowed
        }
        holder.binding.albumDelete.setOnClickListener {
            val prePictureList = pictureViewModel.getPictureArray()
            // 사진 삭제 = list[position] = null
            prePictureList?.set(position, null)
            pictureViewModel.setPictureData(prePictureList!!)
        }
        holder.binding.albumCancel.setOnClickListener {
            // 취소 = View.GONE
            buttonViewList[position] = false
            selectItemIndex = -1
//            notifyItemChanged(position)
            notifyDataSetChanged()
        }
        pictureList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun setData(newList: ArrayList<PictureModel?>) {
        pictureList = newList
        notifyDataSetChanged()
    }
}