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

class FavoritesAdapter(private val itemsList : ArrayList<Photo>) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fav_row,parent,false)
        )
    }

    override fun getItemCount(): Int  = itemsList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun renderItems(items: MutableList<Photo>){
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems() = itemsList

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: Photo) = with(itemView){
            Glide.with(itemView).load(photo.src?.original).into(img_photo)
            tv_photographer_name.text = photo.photographer
            img_fav?.setOnClickListener {
                (it as ImageButton).setImageResource(R.mipmap.favorite_home_select)
                onItemClickListener?.let {
                    it(photo)
                }
            }
        }
    }

    private var onItemClickListener : ((Photo) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Photo) -> Unit)){
        onItemClickListener = listener
    }

}