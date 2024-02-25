package com.vahid.cryptocurrencyapp.domain.use_case

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import android.util.Log
import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDaiUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<DAICoinStats>> = flow {
        var error: String? = null
        try {
            emit(Resource.Loading())
            val response = coinRepository.getDAiCoins()
            if (response.code().toString().contains("2")) {
                val coin = response.body()
                emit(Resource.Success<DAICoinStats>(coin!!))
            } else {
                error = response.raw().toString()
                emit(Resource.Error(response.raw().toString() + response.code().toString()))
            }
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error(error ?: "An unExpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error(error ?: "Could not reach server ,Check Internet Connection"))

        }
    }
}