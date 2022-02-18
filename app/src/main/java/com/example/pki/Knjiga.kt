package com.example.pki

import com.google.gson.annotations.SerializedName

data class Knjiga(
    @SerializedName("id" ) val id:Int,
    @SerializedName("naslov" ) val naslov:String,
    @SerializedName("autor" ) val autor:String,
    @SerializedName("godina" ) val godina:Int,
    @SerializedName("ocena" ) val ocena:Int,
    @SerializedName("brojStrana" ) val brojStrana:Int,
    @SerializedName("opis" ) val opis:String,
    @SerializedName("promocija" ) val promocija:Int,
    @SerializedName("komentari" ) var komentari:MutableList<Komentari>,
    @SerializedName("ocene" ) var ocene:MutableList<Ocene>,
    @SerializedName("slika" ) val slika:Int
    )
