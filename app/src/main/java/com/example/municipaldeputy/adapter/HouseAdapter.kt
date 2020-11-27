package com.example.municipaldeputy.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Human
import kotlinx.android.synthetic.main.item_house.view.*


class HouseAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: MutableList<House> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(this, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as HouseViewHolder) {
            bind(list[position]);
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}

class HouseViewHolder(val adapter: HouseAdapter, view: View) :
    RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    fun bind(item: House) {
        itemView.setOnCreateContextMenuListener(this)
        with(itemView) {
            house_id.text=item.id.toString()
            house_address.text = item.address
            house_floors.text = item.floorCount.toString()
            house_entrances.text = item.entrancesCount.toString()
            house_management_company.text = item.managementCompany
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(v!!.context.getString(R.string.select_option));
        menu.add(0, PHOTO_VIEW, v!!.house_id.text.toString().toInt(), v.context.getString(R.string.house_foto))
        menu.add(0, ACTIVE_VIEW, v!!.house_id.text.toString().toInt(), v.context.getString(R.string.active_list))
        menu.add(0, WORK_DONE_VIEW, v!!.house_id.text.toString().toInt(), v.context.getString(R.string.done_improvement_works))
        menu.add(0, WORK_PLANE_VIEW, v!!.house_id.text.toString().toInt(), v.context.getString(R.string.undone_improvement_works))
    }


}