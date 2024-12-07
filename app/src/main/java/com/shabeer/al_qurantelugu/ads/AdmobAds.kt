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

    // Show Interstitial Ad
    fun showInterstitialAd(activity: Activity) {
        mInterstitialAd?.let {
            it.show(activity)
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    onDismiss?.onDismiss()
                    mInterstitialAd = null
                    loadInterstitialAd(activity)  // Preload next ad after dismissal
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    Log.d("AdmobAds", "Interstitial ad failed to show: ${p0.message}")
                    onDismiss?.onDismiss()  // Notify when ad fails to show
                }
            }
        } ?: run {
            Log.d("AdmobAds", "Interstitial ad was not ready.")
            onDismiss?.onDismiss()  // Handle scenario when ad is not ready
        }
    }

    // Show Rewarded Ad
    fun showRewardedAds(activity: Activity) {
        rewardedAd?.let { ad ->
            ad.show(activity) { rewardItem ->
                val rewardedAmount = rewardItem.amount
                Log.d("RewardedAd", "User earned: $rewardedAmount ${rewardItem.type}")
            }
        } ?: run {
            Log.d("AdmobAds", "The rewarded ad wasn't ready yet.")
        }

        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                onDismiss?.onDismiss()
                rewardedAd = null
                loadRewardedAds(activity)  // Preload next ad after dismissal
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                Log.d("AdmobAds", "Rewarded ad failed to show: ${p0.message}")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d("AdmobAds", "Rewarded ad impression logged.")
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                Log.d("AdmobAds", "Rewarded ad was shown.")
            }
        }
    }

    companion object {
        private var mInterstitialAd: InterstitialAd? = null
        private var rewardedAd: RewardedAd? = null
        private const val TAG = "AdmobAds"

        // Load Interstitial Ad
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
                            Log.d(TAG, "Failed to load interstitial ad: ${adError.message}")
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            mInterstitialAd = interstitialAd
                            Log.d(TAG, "Interstitial ad loaded successfully.")
                        }
                    })
            }
        }

        // Load Rewarded Ad
        fun loadRewardedAds(context: Context) {
            if (AdMobAdUnits.areAdsEnabled) {
                val adRequest = AdRequest.Builder().build()
                RewardedAd.load(
                    context, AdMobAdUnits.REWARDED_AD_UNIT_ID,
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            rewardedAd = null
                            Log.d(TAG, "Failed to load rewarded ad: ${adError.message}")
                        }

                        override fun onAdLoaded(ad: RewardedAd) {
                            rewardedAd = ad
                            Log.d(TAG, "Rewarded ad loaded successfully.")
                        }
                    })
            }
        }
    }
}
