package com.example.municipaldeputy.entity

class House(
    val id:Int?,
    val address:String,
    val entrancesCount:Int,
    val floorCount:Int,
    val managementCompany:String
) {
    override fun toString(): String {
        return address
    }
}