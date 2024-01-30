package com.vahid.cryptocurrencyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StatsDAI(
    @SerializedName("dai-rls")
    val daiRls: DAIRls
)