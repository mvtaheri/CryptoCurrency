package com.vahid.cryptocurrencyapp.presentation.coin_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.vahid.cryptocurrencyapp.R
import com.vahid.cryptocurrencyapp.domain.model.Coin
import com.vahid.cryptocurrencyapp.presentations.CoinDetailsViewModel
import com.vahid.cryptocurrencyapp.presentations.Coins
import com.vahid.cryptocurrencyapp.ui.theme.BackgroundColorMyApp
import com.vahid.cryptocurrencyapp.ui.theme.CryptoCurrencyAppTheme
import com.vahid.cryptocurrencyapp.ui.theme.TextColorPrice
import com.vahid.cryptocurrencyapp.ui.theme.TextColorPrice2
import com.vahid.cryptocurrencyapp.ui.theme.TextColorPrice3

@OptIn(ExperimentalLayoutApi::class)
@Destination(start = true)
@Composable
fun CoinDetailsScreen(
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {
    val dai_state = viewModel.stateDail.value
    val usdt_state = viewModel.stateUsdt.value
    val dollar_price by viewModel.stateDollar.collectAsState(initial = 0)
    val isLoading by viewModel.is_loading.collectAsState()
    var swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    SwipeRefresh(state = swipeRefreshState, onRefresh = { viewModel.onReferesh() }) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.us),
                                contentDescription = "usd",
                                modifier = Modifier
                                    .size(80.dp, 50.dp)
                                    .scale(1f)
                            )
                            Text(
                                text = "USD",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = dollar_price.toString(),
                                fontSize = 50.sp,
                                color = TextColorPrice
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    dai_state.latest?.let { latest ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dai),
                                    contentDescription = "dai",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .scale(1.5f)
                                )
                                Text(
                                    text = "DAI",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val diff =
                                    (latest.toInt() - dollar_price.toString().toInt()) ?: 0
                                Text(
                                    text = latest,
                                    fontSize = 50.sp,
                                    color = TextColorPrice
                                )
                                Text(
                                    text = diff.toString(),
                                    fontSize = 30.sp,
                                    color = if (diff < 0) TextColorPrice3 else TextColorPrice2
                                )

                            }
                        }
                        if (dai_state.error.isNotBlank()) {
                            Text(
                                text = dai_state.error,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            BackgroundColorMyApp.copy(.8f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    usdt_state.latest?.let { latest ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.usdt),
                                    contentDescription = "usdt",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .scale(1.5f)
                                )
                                Text(
                                    text = "USDT",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val diff =
                                    (latest.toInt() - dollar_price.toString().toInt()) ?: 0
                                Text(
                                    text = latest,
                                    fontSize = 50.sp,
                                    color = TextColorPrice
                                )
                                Text(
                                    text = diff.toString(),
                                    fontSize = 30.sp,
                                    color = if (diff < 0) TextColorPrice3 else TextColorPrice2
                                )
                            }
                        }
                        if (usdt_state.error.isNotBlank()) {
                            Text(
                                text = dai_state.error,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        if (usdt_state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoinItem() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(
                width = 1.dp,
                color = BackgroundColorMyApp.copy(.8f),
                shape = RoundedCornerShape(20.dp)
            )
            .background(BackgroundColorMyApp.copy(.8f), shape = RoundedCornerShape(20.dp))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dai),
                    contentDescription = "dai",
                    modifier = Modifier
                        .size(50.dp)
                        .scale(1.5f)
                )
                Text(
                    text = "DAI",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "26,800",
                    fontSize = 50.sp,
                    color = TextColorPrice
                )
                Text(
                    text = "26,800",
                    fontSize = 30.sp,
                    color = TextColorPrice2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoCurrencyAppTheme {
        CoinItem()
    }
}