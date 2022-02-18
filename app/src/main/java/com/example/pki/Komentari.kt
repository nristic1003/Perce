package com.example.pki

import com.google.gson.annotations.SerializedName

data class Komentari(
    @SerializedName("username") val username : String,
    @SerializedName("komentar") val komentar : String,
    @SerializedName("datum") val datum : String
)
