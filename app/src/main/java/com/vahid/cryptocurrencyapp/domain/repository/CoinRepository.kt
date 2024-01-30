package com.vahid.cryptocurrencyapp.domain.repository

import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats

interface CoinRepository {
    suspend fun getUSDTCoins(): USDTCoinStats
    suspend fun getDAiCoins(): DAICoinStats
}