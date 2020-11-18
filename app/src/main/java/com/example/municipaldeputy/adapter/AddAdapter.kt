package com.example.municipaldeputy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Human
import com.example.municipaldeputy.entity.Work
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.item_add_active.view.*
import kotlinx.android.synthetic.main.item_add_district.view.*
import kotlinx.android.synthetic.main.item_add_district.view.spinner
import kotlinx.android.synthetic.main.item_add_house.view.*
import kotlinx.android.synthetic.main.item_add_photo.view.*
import kotlinx.android.synthetic.main.item_add_region.view.*
import kotlinx.android.synthetic.main.item_add_region.view.add_btn
import kotlinx.android.synthetic.main.item_add_street.view.*
import kotlinx.android.synthetic.main.item_add_work.view.*
import java.text.SimpleDateFormat

class AddAdapter(val elementsNumber: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ImageClickListener: OnImageBtnClickListener? = null
    var AddBtnClickListener: OnAddBtnClickListener? = null

    interface OnAddBtnClickListener {
        fun onAddBtnClicked(houseId: Int)
    }

    interface OnImageBtnClickListener {
        fun onImageBtnClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = when (viewType) {
            0 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_region, parent, false)
            1 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_district, parent, false)
            2 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_street, parent, false)
            3 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_house, parent, false)
            4 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_photo, parent, false)
            5 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_work, parent, false)
            6 ->
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_active, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_region, parent, false)
        }
        return AddViewHolder(view, this)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 7 // 7 таблиц, которые могут быть использованы для добавления
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as AddViewHolder) {
            for (i in 0 until elementsNumber) {
                bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return elementsNumber
    }
}

class AddViewHolder(view: View, val adapter: AddAdapter) : RecyclerView.ViewHolder(view) {
    val dbManager = DBManager(view.context).openRead()
    fun bind() {
        with(itemView) {
            when (itemViewType) {
                0 -> {//region
                    add_btn.setOnClickListener {
                        dbManager.insertRegion(region_name_edt.text.toString())
                    }
                }
                1 -> {//district
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchRegion()
                        )
                    add_btn.setOnClickListener {
                        dbManager.insertDistrict(
                            district_name_edt.text.toString(),
                            spinner.selectedItemPosition + 1//пока нет удаления = id
                        )
                    }
                }
                2 -> {//street
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchDistrict(null)
                        )
                    add_btn.setOnClickListener {
                        dbManager.insertStreet(
                            street_name_edt.text.toString(),
                            spinner.selectedItemPosition + 1
                        )
                    }
                }
                3 -> {//house
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchStreet(null)
                        )
                    add_btn.setOnClickListener {
                        dbManager.insertHouse(
                            House(
                                null, address_edt.text.toString(),
                                Integer.parseInt(number_of_entrances_edt.text.toString()),
                                Integer.parseInt(number_of_floors_edt.text.toString()),
                                management_company_edt.text.toString()
                            ),
                            spinner.selectedItemPosition + 1
                        )
                    }
                }
                4 -> {//photo
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchHouse(null)
                        )
                    preview_img.setOnClickListener {
                        adapter.ImageClickListener!!.onImageBtnClicked()
                    }
                    add_btn.setOnClickListener {
                        adapter.AddBtnClickListener!!.onAddBtnClicked(spinner.selectedItemPosition + 1)
                    }
                }
                5 -> {//work
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchHouse(null)
                        )
                    add_btn.setOnClickListener {
                        dbManager.insertWork(
                            Work(
                                null,
                                work_name_edt.text.toString(),
                                SimpleDateFormat("dd.mm.yyyy").parse(work_date_edt.text.toString()),
                                construction_company_edt.text.toString()
                            ),
                            done_chbx.isChecked,
                            spinner.selectedItemPosition + 1
                        )
                    }
                }
                6 -> {//asset
                    spinner.adapter =
                        ArrayAdapter(
                            this.context,
                            R.layout.multiline_spinner_dropdown_item,
                            dbManager.fetchHouse(null)
                        )
                    add_btn.setOnClickListener {
                        dbManager.insertActive(
                            Human(
                                null,
                                surname_edt.text.toString(),
                                name_edt.text.toString(),
                                patronymic_edt.text.toString(),
                                Integer.parseInt(apartment_number_edt.text.toString()),
                                phone_number_edt.text.toString(),
                                mail_edt.text.toString()
                            ),
                            spinner.selectedItemPosition + 1
                        )
                    }
                }
            }
        }
    }
}