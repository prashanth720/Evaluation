package com.example.evaluation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evaluation.R
import com.example.evaluation.network.models.Photo
import com.example.evaluation.network.models.Video
import kotlinx.android.synthetic.main.photo_row.view.*

class VideosAdapter(private val videosList : ArrayList<Video>) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video_row,parent,false)
        )
    }

    override fun getItemCount(): Int  = videosList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videosList[position])
    }

    fun renderVideos(videos: MutableList<Video>){
        videosList.addAll(videos)
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(video: Video) = with(itemView){
            Glide.with(itemView).load(video.image).into(img_photo)
            tv_photographer_name.text = video.user?.name
            img_fav.setOnClickListener {
                (it as ImageButton).setImageResource(R.mipmap.favorite_home_select)
            }
        }
    }

    private var onItemClickListener : ((Photo) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Photo) -> Unit)){
        onItemClickListener = listener
    }

}