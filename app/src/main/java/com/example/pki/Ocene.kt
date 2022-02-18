package com.example.pki

import com.google.gson.annotations.SerializedName

data class Ocene(
    @SerializedName("username") val username : String,
    @SerializedName("ocena") val ocena : Int

    )
