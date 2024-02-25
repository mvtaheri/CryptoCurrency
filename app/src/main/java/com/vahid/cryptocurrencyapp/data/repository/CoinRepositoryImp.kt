package com.vahid.cryptocurrencyapp.data.repository

import com.vahid.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import retrofit2.Response

import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getUSDTCoins(): Response<USDTCoinStats> {
        return api.getUSDTCoins()
    }

    override suspend fun getDAiCoins(): Response<DAICoinStats> {
        return api.getDAiCoins()
    }
}