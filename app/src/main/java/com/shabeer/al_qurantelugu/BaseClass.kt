package com.shabeer.al_qurantelugu

import android.app.Application
import com.shabeer.al_qurantelugu.ads.AdmobAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseClass : Application() {
    override fun onCreate() {
        super.onCreate()
        AdmobAds.loadInterstitialAd(this)
        AdmobAds.loadRewardedAds(this)
    }

}