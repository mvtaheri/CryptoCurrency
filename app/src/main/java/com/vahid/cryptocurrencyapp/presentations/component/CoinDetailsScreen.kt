package com.vahid.cryptocurrencyapp.presentation.coin_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.vahid.cryptocurrencyapp.presentations.CoinDetailsViewModel
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
    val dollar_price by viewModel.stateDollar.collectAsState(initial = 0)
    val dai by viewModel.dai.collectAsState(initial = 0)
    val usdt by viewModel.usdt.collectAsState(initial = 0)
    val isLoading by viewModel.isLoading.collectAsState()
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
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Center
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
                                color = Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dollar_price.toString(),
                                fontSize = 50.sp,
                                fontWeight = FontWeight.ExtraBold,
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
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dai),
                                contentDescription = "dai",
                                modifier = Modifier
                                    .size(50.dp)
                                    .scale(1f)
                            )
                            Text(
                                text = "DAI",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = dai.toString(),
                                fontSize = 50.sp,
                                color = TextColorPrice,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "اختلاف با دلار آزاد",
                                    fontSize = 15.sp,
                                    color = TextColorPrice
                                )
                                val diff =
                                    (dai.toString().toInt() - dollar_price.toString().toInt()) ?: 0
                                Text(
                                    text = diff.toString(),
                                    fontSize = 40.sp,
                                    color = if (diff < 0) TextColorPrice3 else TextColorPrice2
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "اختلاف با دلار تعادلی",
                                    fontSize = 15.sp,
                                    color = TextColorPrice
                                )
                                val diff_tadoly =
                                    (dai.toString().toInt() - dollar_price.toString().toInt() + 600)
                                        ?: 0
                                Text(
                                    text = diff_tadoly.toString(),
                                    fontSize = 40.sp,
                                    color = if (diff_tadoly < 0) TextColorPrice3 else TextColorPrice2
                                )
                            }
                        }
                    }
//                    if (usdt_state.isLoading) {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                    }
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
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.usdt),
                                contentDescription = "usdt",
                                modifier = Modifier
                                    .size(50.dp)
                                    .scale(1f)
                            )
                            Text(
                                text = "USDT",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = usdt.toString(),
                                fontSize = 50.sp,
                                color = TextColorPrice,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "اختلاف با دلار آزاد",
                                    fontSize = 15.sp,
                                    color = TextColorPrice
                                )
                                val diff =
                                    (usdt.toString().toInt() - dollar_price.toString().toInt()) ?: 0
                                Text(
                                    text = diff.toString(),
                                    fontSize = 40.sp,
                                    color = if (diff < 0) TextColorPrice3 else TextColorPrice2
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "اختلاف با دلار تعادلی",
                                    fontSize = 15.sp,
                                    color = TextColorPrice
                                )
                                val diff_tadoly_2 =
                                    (usdt.toString().toInt() - dollar_price.toString()
                                        .toInt() + 600) ?: 0
                                Text(
                                    text = diff_tadoly_2.toString(),
                                    fontSize = 40.sp,
                                    color = if (diff_tadoly_2 < 0) TextColorPrice3 else TextColorPrice2
                                )
                            }
                        }
                    }
//                    if (usdt_state.isLoading) {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                    }
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
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dai),
                    contentDescription = "dai",
                    modifier = Modifier
                        .size(50.dp)
                        .scale(1f)
                )
                Text(
                    text = "DAI",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "26,800",
                    fontSize = 50.sp,
                    color = TextColorPrice,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "اختلاف با دلار آزاد",
                        fontSize = 15.sp,
                        color = TextColorPrice
                    )
                    Text(
                        text = "26,800",
                        fontSize = 40.sp,
                        color = TextColorPrice
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "اختلاف با دلار تعادلی",
                        fontSize = 15.sp,
                        color = TextColorPrice
                    )
                    Text(
                        text = "26,800",
                        fontSize = 40.sp,
                        color = TextColorPrice
                    )
                }
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