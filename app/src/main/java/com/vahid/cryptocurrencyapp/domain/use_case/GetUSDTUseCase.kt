package com.vahid.cryptocurrencyapp.domain.use_case

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
            try {
                emit(Resource.Loading())
                val coin = coinRepository.getUSDTCoins()
                emit(Resource.Success<USDTCoinStats>(coin))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unExpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error("Could not reach server ,Check Internet Connection"))
            }
        }
}