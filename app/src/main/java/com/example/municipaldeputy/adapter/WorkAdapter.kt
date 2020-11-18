package com.example.municipaldeputy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Work
import kotlinx.android.synthetic.main.item_work.view.*
import java.text.SimpleDateFormat

class WorkAdapter(val list: List<Work>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            work_date.text=SimpleDateFormat("dd.mm.yyyy").format(item.date)
            worker_name.text=item.worker
        }
    }
}