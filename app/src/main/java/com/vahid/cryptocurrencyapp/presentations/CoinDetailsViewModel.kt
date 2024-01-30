package com.vahid.cryptocurrencyapp.presentations

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.domain.use_case.GetDaiUseCase
import com.vahid.cryptocurrencyapp.domain.use_case.GetDollarPriceUseCase
import com.vahid.cryptocurrencyapp.domain.use_case.GetUSDTUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getDailUseCase: GetDaiUseCase,
    private val getUSDTUseCase: GetUSDTUseCase,
    private val getDollarPriceUseCase: GetDollarPriceUseCase
) : ViewModel() {

    private val _stateDail = mutableStateOf(DaiCoindetailState())
    var stateDail: State<DaiCoindetailState> = _stateDail

    val _stateUsdt = mutableStateOf(UsdtCoindetailState())
    var stateUsdt: State<UsdtCoindetailState> = _stateUsdt

    val _stateDollar = MutableSharedFlow<String>(1)
    var stateDollar = _stateDollar.asSharedFlow()

    private val _is_loading = MutableStateFlow(false)
    val is_loading = _is_loading.asStateFlow()

    init {
        onReferesh()
    }

    fun onReferesh() {
        viewModelScope.launch {
            _is_loading.value = true
            getDollarFormSpoap()
            getUSDT()
            getDAI()
            delay(1000)
            _is_loading.value = false
        }
    }


    fun getDollar() {
        viewModelScope.launch {
            getDollarFormSpoap()
        }
    }

    private suspend fun getDollarFormSpoap() {
        val doc: Document
        var dollarEnPrice = ""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doc = Jsoup.connect("https://irarz.com/").get()
                val dollarPrice: Elements = doc.select("span#usdmax")
                Log.d("Text : ", dollarPrice.text())
                var en = '0'
                for (ch in dollarPrice.text()) {
                    en = ch
                    when (ch) {
                        '۰' -> en = '0'
                        '۱' -> en = '1'
                        '۲' -> en = '2'
                        '۳' -> en = '3'
                        '۴' -> en = '4'
                        '۵' -> en = '5'
                        '۶' -> en = '6'
                        '۷' -> en = '7'
                        '۸' -> en = '8'
                        '۹' -> en = '9'
                        ',' -> continue
                    }
                    dollarEnPrice = dollarEnPrice + en
                }
                Log.d("result", dollarEnPrice)
                val formatDollar = dollarEnPrice.toInt() / 10
                _stateDollar.emit(formatDollar.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getDAI() {
        getDailUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val latest = result.data?.stats?.daiRls?.latest ?: "0"
                    val format_latest = latest.toInt() / 10
                    _stateDail.value = DaiCoindetailState(latest = format_latest.toString())
                }

                is Resource.Error -> {
                    _stateDail.value = DaiCoindetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _stateDail.value = DaiCoindetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUSDT() {
        getUSDTUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val latest = result.data?.stats?.usdtRls?.latest ?: "0"
                    val format_latest = latest.toInt() / 10
                    _stateUsdt.value = UsdtCoindetailState(latest = format_latest.toString())
                }

                is Resource.Error -> {
                    _stateUsdt.value = UsdtCoindetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _stateUsdt.value = UsdtCoindetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}