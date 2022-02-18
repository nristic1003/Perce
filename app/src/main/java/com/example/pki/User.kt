package com.example.pki

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id" ) var id:Int,
    @SerializedName("username" ) var username:String,
    @SerializedName("password" ) var password:String,
    @SerializedName("name" )     var name:String,
    @SerializedName("lastname" ) var lastname:String,
    @SerializedName("address" )  var address:String,
    @SerializedName("contact" )  var contact:String,
    @SerializedName("isAdmin" )  var isAdmin:Boolean
)
