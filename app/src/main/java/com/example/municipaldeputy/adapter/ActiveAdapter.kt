package com.example.municipaldeputy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Human
import kotlinx.android.synthetic.main.item_human.view.*

class ActiveAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: MutableList<Human> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_human, parent, false)
        return ActiveViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as ActiveViewHolder) {
            bind(list[position]);
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ActiveViewHolder(view: View):RecyclerView.ViewHolder(view)  {
    fun bind(item: Human) {
        with(itemView){
            full_name.text="${item.surname} ${item.name} ${item.patronymic}"
            apartment_number.text=item.apartmentNumber.toString()
            phone_number.text=item.phoneNumber
            mail.text=item.mail
        }
    }
}