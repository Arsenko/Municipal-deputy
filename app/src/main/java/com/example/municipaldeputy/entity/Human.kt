package com.example.municipaldeputy.entity

class Human(
    val id:Int,
    val surname:String,
    val name:String,
    val patronymic:String,
    val apartmentNumber:Int,
    val phoneNumber:String,
    val mail:String
) {
    override fun toString(): String {
        return "$surname $name $patronymic"
    }
}