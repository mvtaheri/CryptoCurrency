package com.vahid.cryptocurrencyapp.data.repository

import com.vahid.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository

import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getUSDTCoins(): USDTCoinStats {
        return api.getUSDTCoins()
    }

    override suspend fun getDAiCoins(): DAICoinStats {
        return api.getDAiCoins()
    }
}