package com.vahid.cryptocurrencyapp.data.remote

import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDetaisDto
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ConinPaprikaApi {
    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetaisDto
}