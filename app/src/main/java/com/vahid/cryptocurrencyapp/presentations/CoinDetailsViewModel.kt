package com.vahid.cryptocurrencyapp.presentations

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahid.cryptocurrencyapp.common.Resource
import com.vahid.cryptocurrencyapp.domain.use_case.GetDaiUseCase
import com.vahid.cryptocurrencyapp.domain.use_case.GetUSDTUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject


@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getDailUseCase: GetDaiUseCase,
    private val getUSDTUseCase: GetUSDTUseCase
) : ViewModel() {

    val _stateDollar = MutableSharedFlow<String>(1)
    var stateDollar = _stateDollar.asSharedFlow()

    val _dai = MutableSharedFlow<String>(1)
    var dai = _dai.asSharedFlow()

    val _usdt = MutableSharedFlow<String>(1)
    var usdt = _usdt.asSharedFlow()


    private val _error_message = MutableStateFlow("")
    val error_message = _error_message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                onReferesh()
            }
        }, 1, 5000)
    }

    fun onReferesh() {
        _isLoading.value = true
        viewModelScope.launch {
            getDollarFormSpoap()
            getUSDT()
            getDAI()
        }
        _isLoading.value = false
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
                var formatDollar = 0
                if (!dollarEnPrice.isNullOrBlank()) {
                    Log.d("result", dollarEnPrice)
                    formatDollar = dollarEnPrice.toInt() / 10
                }
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
//                    _stateDail.value = DaiCoindetailState(latest = format_latest.toString())
                    _dai.emit(format_latest.toString())
                }

                is Resource.Error -> {
                    _error_message.emit(result.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
//                    _stateDail.value = DaiCoindetailState(isLoading = true)
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
                    _usdt.emit(format_latest.toString())
//                    _stateUsdt.value = UsdtCoindetailState(latest = format_latest.toString())
                }

                is Resource.Error -> {
                    _error_message.emit(result.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
//                    _stateUsdt.value = UsdtCoindetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}