package com.vahid.cryptocurrencyapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StatsUSDT(
    @SerializedName("usdt-rls")
    val usdtRls: USDTRls
)