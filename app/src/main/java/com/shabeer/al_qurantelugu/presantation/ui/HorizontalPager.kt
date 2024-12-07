package com.shabeer.al_qurantelugu.presantation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.shabeer.al_qurantelugu.data.model.PagerData
import com.shabeer.al_qurantelugu.presantation.viewmodels.PagerDataViewModel
import network.chaintech.sdpcomposemultiplatform.sdp


@Composable
fun PagerView(pagerDataViewModel: PagerDataViewModel = hiltViewModel()) {
    pagerDataViewModel.fetchPagerImages()
    val pagerImages = pagerDataViewModel.pagerData.observeAsState(emptyList())
    val pagerState = rememberPagerState(pageCount = { pagerImages.value.size })


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            ImageCard(pagerImages.value[page])
        }
        PagerIndication(pagerImages, pagerState)
    }
}


@Composable
fun ImageCard(imageUrl: PagerData) {
    val imagePath = if (imageUrl.image_path != null) {
        "https://teluguislamicworld.in/" + imageUrl.image_path
    } else {
        null
    }

    var imageLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.sdp)
            .height(150.sdp)
            .clickable {
                openBrowser(imageUrl.images_url.toString(), context)

            }
    ) {
        Box {
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onSuccess = { imageLoading = false },
                onError = { imageLoading = false }
            )



            if (imageLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center), color = primaryColor()
                )
            }

        }
    }
}


@Composable
fun PagerIndication(pagerData: State<List<PagerData>>, pagerState: PagerState) {
    Row(horizontalArrangement = Arrangement.Center) {
        pagerData.value.forEachIndexed { index, pagerData ->
            if (index == pagerState.currentPage) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(5.sdp)
                        .background(primaryColor())
                )
            } else {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(5.sdp)
                        .background(Color.Gray)
                )
            }
            Spacer(modifier = Modifier.width(2.sdp))
        }
    }
}

private fun openBrowser(url: String, context: Context) {
    if (url == "") {
        Toast
            .makeText(context, "No Links Available", Toast.LENGTH_SHORT)
            .show()
    } else {

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

}