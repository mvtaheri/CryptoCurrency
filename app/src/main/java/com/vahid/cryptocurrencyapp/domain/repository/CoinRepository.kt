package com.vahid.cryptocurrencyapp.domain.repository

import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import retrofit2.Response

interface CoinRepository {
    suspend fun getUSDTCoins(): Response<USDTCoinStats>
    suspend fun getDAiCoins(): Response<DAICoinStats>
}