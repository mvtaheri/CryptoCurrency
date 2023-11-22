package com.vahid.cryptocurrencyapp.presentation.coin_details

import com.vahid.cryptocurrencyapp.domain.model.CoinDetails

data class CoindetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetails? = null,
    val error: String = ""
)
