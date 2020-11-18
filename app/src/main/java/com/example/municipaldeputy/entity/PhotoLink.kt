package com.example.municipaldeputy.entity

class PhotoLink(
    val id:Int?,
    val link:String,
    val isResource:Int
) {
    override fun toString(): String {
        return link
    }
}