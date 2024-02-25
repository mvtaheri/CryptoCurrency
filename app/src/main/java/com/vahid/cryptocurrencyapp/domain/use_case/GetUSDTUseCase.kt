package com.vahid.cryptocurrencyapp.domain.use_case

import android.util.Log
import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetUSDTUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<USDTCoinStats>> = flow {
        var error: String? = null
        try {
            emit(Resource.Loading())
            val response = coinRepository.getUSDTCoins()
            if (response.code().toString().contains("2")) {
                Log.d("response code 200", response.body().toString() + response.code())
                val coin = response.body()
                emit(Resource.Success<USDTCoinStats>(coin!!))
            } else {
                error = response.raw().toString()
                emit(Resource.Error(response.raw().toString() + response.code().toString()))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(error ?: "An unExpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error(error ?: "Could not reach server ,Check Internet Connection"))

        }
    }
}