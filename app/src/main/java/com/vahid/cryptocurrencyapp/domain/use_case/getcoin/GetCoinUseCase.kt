package com.vahid.cryptocurrencyapp.domain.use_case.getcoin

import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.data.remote.dto.toCoin
import com.vahid.cryptocurrencyapp.data.remote.dto.toCoinDetails
import com.vahid.cryptocurrencyapp.domain.model.Coin
import com.vahid.cryptocurrencyapp.domain.model.CoinDetails
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetails>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetails()
            emit(Resource.Success<CoinDetails>(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unExpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Could not reach server ,Check Internet Connection"))
        }
    }
}

