package com.vahid.cryptocurrencyapp.domain.use_case.getcoins

import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.data.remote.dto.toCoin
import com.vahid.cryptocurrencyapp.domain.model.Coin
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success<List<Coin>>(coins))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unExpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Coin>>("Could not reach server ,Check Internet Connection"))
        }
    }


}