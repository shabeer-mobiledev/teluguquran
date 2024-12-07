package com.shabeer.al_qurantelugu.presantation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shabeer.al_qurantelugu.DetailActivity
import com.shabeer.al_qurantelugu.R
import com.shabeer.al_qurantelugu.ads.AdmobAds
import com.shabeer.al_qurantelugu.ads.AdmobInterFace
import com.shabeer.al_qurantelugu.data.model.QuranData
import com.shabeer.al_qurantelugu.presantation.viewmodels.QuranViewModel
import com.shabeer.al_qurantelugu.util.Constents
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun SurahScreen(viewModel: QuranViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val surahNamesList = viewModel.pagerData.observeAsState(emptyList())
    // State to hold the search query
    var searchQuery by remember { mutableStateOf("") }


    var count = remember { mutableStateOf(0) }

    Column {
        NameComposable("దివ్య ఖురాన్") {
            if (count.value == 0) {
                count.value++
            } else {
                count.value--
            }

        }

        if (count.value == 1) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Surah") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = primaryColor(), focusedBorderColor = primaryColor(),
                    disabledBorderColor = primaryColor(), focusedLabelColor = primaryColor(),
                    disabledTextColor = primaryColor(), disabledLabelColor = primaryColor(),
                    cursorColor = primaryColor()
                ),
                shape = MaterialTheme.shapes.large
            )

        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (Constents.isInternetAvailable(context)) {
                if (surahNamesList.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = primaryColor())
                    }

                } else {

                    val filteredSurahList = surahNamesList.value.filter {
                        it.surahnames.contains(searchQuery, ignoreCase = true) ||
                                it.id.toString().contains(searchQuery)
                    }

                    LazyColumn {
                        items(filteredSurahList) {
                            SurahNameComposable(it, context)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SurahNameComposable(quranData: QuranData, context: Context) {
    context as Activity
    Card(
        onClick = {
            AdmobAds(object : AdmobInterFace {
                override fun onDismiss() {
                    Intent(context, DetailActivity::class.java).apply {
                        this.putExtra("pdfUrl", quranData.pdfurl)
                        context.startActivity(this)
                    }
                }

            }).showInterstitialAd(context)

        },
        modifier = Modifier
            .fillMaxWidth()
            .height(75.sdp)
            .padding(4.sdp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.sdp
        )
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = onPrimaryColor()),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        quranData.id.toString(),
                        modifier = Modifier.padding(start = 15.sdp),
                        fontSize = 7.ssp
                    )
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = null,
                        Modifier
                            .padding(start = 15.sdp)
                            .size(40.sdp),
                        colorFilter = ColorFilter.tint(primaryColor())
                    )
                }

                Spacer(modifier = Modifier.width(20.sdp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        quranData.surahnames,
                        fontFamily = FontFamily(Font(R.font.ramabhadra)),
                        textAlign = TextAlign.Center,
                        fontSize = 15.ssp,
                        color = primaryColor()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            "రుకు : ${quranData.ruku}",
                            textAlign = TextAlign.Center,
                            fontSize = 11.ssp,
                            color = primaryColor()
                        )
                        Spacer(modifier = Modifier.width(5.sdp))
                        Text(
                            "అయతే : ${quranData.ayathe}",
                            textAlign = TextAlign.Center,
                            fontSize = 11.ssp,
                            color = primaryColor()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NameComposable(title: String, onClick: () -> Unit) {
    // Name Card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.sdp)
            .padding(4.sdp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.sdp
        )
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(color = onPrimaryColor()),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        title,
                        color = primaryColor(),
                        fontFamily = FontFamily(Font(R.font.ramabhadra)),
                        fontSize = 14.ssp,
                        modifier = Modifier.padding(top = 5.sdp)
                    )
                    Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxSize()) {

                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = primaryColor(),
                            modifier = Modifier
                                .padding(end = 15.sdp)
                                .clickable { onClick() }
                        )
                    }

                }
            }

        }
    }

}


