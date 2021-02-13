package com.example.evaluation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evaluation.R
import com.example.evaluation.network.models.Photo
import kotlinx.android.synthetic.main.photo_row.view.*

class PhotosAdapter(private val photosList : ArrayList<Photo>) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_row,parent,false)
        )
    }

    override fun getItemCount(): Int  = photosList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photosList[position])
    }

    fun renderPhotos(photos: MutableList<Photo>){
        photosList.addAll(photos)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: Photo) = with(itemView){
            Glide.with(itemView).load(photo.src?.original).into(img_photo)
            tv_photographer_name.text = photo.photographer
            img_fav.setOnClickListener {
                (it as ImageButton).setImageResource(R.mipmap.favorite_home_select)
                onItemFavClickListener?.let {
                    it(photo)
                }
            }
            setOnClickListener {
                onItemClickListener?.let {
                    it(photo)
                }
            }
        }
    }

    private var onItemFavClickListener : ((Photo) -> Unit)? = null

    private var onItemClickListener : ((Photo) -> Unit)? = null

    fun setOnItemFavClickListener(listener: ((Photo) -> Unit)){
        onItemFavClickListener = listener
    }

    fun setOnItemClickListener(listener: ((Photo) -> Unit)){
        onItemClickListener = listener
    }


}