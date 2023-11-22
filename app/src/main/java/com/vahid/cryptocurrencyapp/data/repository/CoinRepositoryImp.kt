package com.vahid.cryptocurrencyapp.data.repository

import com.vahid.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDetaisDto
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDto
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetaisDto {
        return api.getCoinById(coinId)
    }
}