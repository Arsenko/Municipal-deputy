package com.example.municipaldeputy.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import kotlinx.android.synthetic.main.item_pager.view.*

class PageAdapter(val list: List<Drawable>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) = holder.itemView.run {
        with(holder as PagerViewHolder) {
            bind(list[position]);
        }
    }
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Drawable) {
        with(itemView) {
            house_image.setImageDrawable(item)
        }
    }
}