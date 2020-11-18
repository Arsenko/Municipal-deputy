package com.example.municipaldeputy.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import kotlinx.android.synthetic.main.item_pager.view.*

class PhotoAdapter(val list: List<Drawable>) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.itemView.run {
        with(holder) {
            bind(list[position]);
        }
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Drawable) {
        with(itemView) {
            house_image.setImageDrawable(item)
        }
    }
}