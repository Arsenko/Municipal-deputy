package com.example.municipaldeputy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Work
import kotlinx.android.synthetic.main.item_work.view.*
import java.text.SimpleDateFormat

class WorkAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: MutableList<Work> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_work, parent, false)
        return WorkViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as WorkViewHolder) {
            bind(list[position]);
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class WorkViewHolder(view: View):RecyclerView.ViewHolder(view)  {
    fun bind(item: Work) {
        with(itemView){
            work_name.text=item.name
            work_date.text=item.date
            worker_name.text=item.worker
        }
    }
}