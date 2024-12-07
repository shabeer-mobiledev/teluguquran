package com.shabeer.al_qurantelugu.presantation.ui

import SubscriptionScreen
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.shabeer.al_qurantelugu.R
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits
import com.shabeer.al_qurantelugu.ads.AdmobAds
import com.shabeer.al_qurantelugu.ads.AdmobInterFace
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun PdfScreen(
    navHostController: NavHostController, url: String,
    lifecycleOwner: LifecycleOwner,
    activity: Activity,
) {
    NavHost(navHostController, startDestination = "pdfviewer") {
        composable("pdfviewer") {
            PdfViewer(url, lifecycleOwner, activity, navHostController)
        }
        composable("premium") {
            SubscriptionScreen(activity, navHostController)
        }
    }
}

@Composable
fun PdfViewer(
    url: String,
    lifecycleOwner: LifecycleOwner,
    activity: Activity,
    navHostController: NavHostController
) {
    val isLoding = remember { mutableStateOf(false) }
    val progresss = remember { mutableStateOf(0) }
    val checkOrientationState = activity.resources.configuration.orientation
    val isCardVisible = remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PdfRendererViewCompose(
                url = url,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                lifecycleOwner = lifecycleOwner,
                statusCallBack = object : PdfRendererView.StatusCallBack {
                    override fun onError(error: Throwable) {}
                    override fun onPageChanged(currentPage: Int, totalPage: Int) {}
                    override fun onPdfLoadProgress(
                        progress: Int, downloadedBytes: Long, totalBytes: Long?
                    ) {
                        progresss.value = progress
                    }

                    override fun onPdfLoadStart() {
                        isLoding.value = true
                    }

                    override fun onPdfLoadSuccess(absolutePath: String) {
                        isLoding.value = false
                    }
                }
            )

            if (checkOrientationState == Configuration.ORIENTATION_LANDSCAPE) {
                isCardVisible.value = false
            }
            if (isCardVisible.value) {
                AdMobBannerAd(Modifier)
            }
        }

        if (isCardVisible.value) {
            OrientationButton(activity, navHostController)
        }

        if (isLoding.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.padding(top = 10.sdp))
                    Text(text = "Loading.. ${progresss.value}%")
                }
            }
        }
    }
}

@Composable
fun AdMobBannerAd(modifier: Modifier = Modifier) {
    if (AdMobAdUnits.areAdsEnabled) {
        AndroidView(
            modifier = modifier.fillMaxWidth(),
            factory = {
                AdView(it).apply {
                    setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                    adUnitId = AdMobAdUnits.BANNER_AD_UNIT_ID
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}

@Composable
fun OrientationButton(activity: Activity, navHostController: NavHostController) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .padding(bottom = 50.sdp, end = 15.sdp)
            .fillMaxSize()
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = onPrimaryColor()),
            modifier = Modifier
                .size(40.sdp)
                .clickable {
                    if (AdMobAdUnits.areAdsEnabled) {
                        showPopUp(activity, navHostController)
                    } else {
                        changeOrientation(activity)
                    }
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.reshot_icon_restart_phone_v4a9nds6lb),
                    contentDescription = null,
                    modifier = Modifier.size(20.sdp),
                    colorFilter = ColorFilter.tint(primaryColor())
                )
            }
        }
    }
}

private fun showPopUp(activity: Activity, navHostController: NavHostController) {
    val dialogBox = android.app.AlertDialog.Builder(activity)
    dialogBox.setIcon(R.mipmap.ic_launcher)
    dialogBox.setTitle("Unlock Full-Screen Mode")
    dialogBox.setMessage("Get one-time premium access! Watch a quick ad to enjoy full-screen viewing, or choose 'Unlock Premium' for unlimited access.")
    dialogBox.setCancelable(false)

    dialogBox.setPositiveButton("Watch Now") { _, _ ->
        Toast.makeText(activity, "Ad Showed", Toast.LENGTH_SHORT).show()
        AdmobAds(object : AdmobInterFace {
            override fun onDismiss() {
                changeOrientation(activity)
            }
        }).showRewardedAds(activity)
    }

    dialogBox.setNegativeButton("Unlock Premium") { _, _ ->
        navHostController.navigate("premium")
    }

    dialogBox.setNeutralButton("Cancel") { dialog, _ ->
        dialog.dismiss()
        Toast.makeText(activity, "Action Canceled", Toast.LENGTH_SHORT).show()
    }

    dialogBox.show()
}

private fun changeOrientation(activity: Activity) {
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}
