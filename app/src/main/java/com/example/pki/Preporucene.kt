package com.example.pki

import com.google.gson.annotations.SerializedName

data class Preporucene(

    @SerializedName("id") var id:Int,
    @SerializedName("bookId") var bookId:Int,
    @SerializedName("forUser") var forUsername:String,
    @SerializedName("fromUser") var fromUsername:String,


)
