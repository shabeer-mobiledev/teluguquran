package com.shabeer.al_qurantelugu.ads

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits.mInterstitialAd
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits.rewardedAd

class AdmobAds(private val onDismiss: AdmobInterFace? = null) {

    constructor() : this(null)

    fun showInterstitialAd(activity: Activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    onDismiss?.onDismiss()
                    mInterstitialAd = null
                    loadInterstitialAd(activity)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    onDismiss?.onDismiss()
                }
            }
        } else {
            onDismiss?.onDismiss()
        }
    }


    fun showRewardedAds(activity: Activity) {

        rewardedAd?.let { ad ->
            ad.show(activity) { rewardItem ->
                val rewardedAmount = rewardItem.type
                Log.d("Amount", rewardedAmount.toString())
            }
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                onDismiss?.onDismiss()
                rewardedAd = null
                loadRewardedAds(activity)
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
            }
        }


    }

    companion object {
        fun loadInterstitialAd(context: Context) {
            if (AdMobAdUnits.areAdsEnabled) {
                val adRequest = AdRequest.Builder().build()

                InterstitialAd.load(
                    context,
                    AdMobAdUnits.INTERSTITIAL_AD_UNIT_ID,
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            mInterstitialAd = null
                            Log.d("ADD ERROR", adError.message)
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            mInterstitialAd = interstitialAd
                            Log.d("ADD Loaded", "ADD Loaded Sucess")
                        }
                    })
            }
        }


        fun loadRewardedAds(context: Context) {
            if (AdMobAdUnits.areAdsEnabled) {
                val adRequest = AdRequest.Builder().build()
                RewardedAd.load(
                    context, AdMobAdUnits.REWARDED_AD_UNIT_ID,
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            adError.toString().let { Log.d(TAG, it) }
                            rewardedAd = null
                        }

                        override fun onAdLoaded(ad: RewardedAd) {
                            Log.d("TAG", "Ad was loaded.")
                            rewardedAd = ad
                        }
                    })

            }
        }
    }
}

