package com.vahid.cryptocurrencyapp.data.remote

import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinPaprikaApi {
    @GET("market/stats")
    suspend fun getUSDTCoins(
        @Query("srcCurrency") srcCurrency: String = "usdt",
        @Query("dstCurrency") dstCurrency: String = "rls"
    ): Response<USDTCoinStats>

    @GET("market/stats")
    suspend fun getDAiCoins(
        @Query("srcCurrency") srcCurrency: String = "dai",
        @Query("dstCurrency") dstCurrency: String = "rls"
    ): Response<DAICoinStats>
}