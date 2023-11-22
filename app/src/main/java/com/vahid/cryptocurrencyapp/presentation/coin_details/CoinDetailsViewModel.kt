package com.vahid.cryptocurrencyapp.presentation.coin_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahid.cryptocurrencyapp.common.Constance
import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.domain.use_case.getcoin.GetCoinUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CoinDetailsViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoindetailState())
    val state: State<CoindetailState> = _state

    init {
        savedStateHandle.get<String>(Constance.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoindetailState(coin = result.data)
                }

                is Resource.Error -> {
                    _state.value = CoindetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CoindetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}