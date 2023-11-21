package com.vahid.cryptocurrencyapp.domain.repository

import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDetaisDto
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetaisDto
}