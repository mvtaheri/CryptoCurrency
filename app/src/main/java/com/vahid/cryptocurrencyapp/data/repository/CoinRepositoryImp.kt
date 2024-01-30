package com.vahid.cryptocurrencyapp.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.vahid.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDetaisDto
import com.vahid.cryptocurrencyapp.data.remote.dto.CoinDto
import com.vahid.cryptocurrencyapp.data.remote.dto.DAICoinStats
import com.vahid.cryptocurrencyapp.data.remote.dto.USDTCoinStats
import com.vahid.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getUSDTCoins(): USDTCoinStats {
        return api.getUSDTCoins()
    }

    override suspend fun getDAiCoins(): DAICoinStats {
        return api.getDAiCoins()
    }

    override suspend fun getDollarPrice(): String {
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
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return dollarEnPrice
    }
}