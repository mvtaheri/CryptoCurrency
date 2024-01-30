package com.vahid.cryptocurrencyapp.data.remote.dto

data class DAICoinStats(
    val status: String,
    val stats: StatsDAI
)

//fun DAICoinStats.toDai(): DAIRls {
//    return DAIRls(
//        isClosed = stats.daiRls.isClosed,
//        val bestSell : String,
//    val bestBuy: String,
//    val volumeSrc: String,
//    val volumeDst: String,
//    val latest: String,
//    val mark: String,
//    val dayLow: String,
//    val dayHigh: String,
//    val dayOpen: String,
//    val dayClose: String,
//    val dayChange: String
//    )
//}
