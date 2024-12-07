package com.shabeer.al_qurantelugu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits
import com.shabeer.al_qurantelugu.presantation.ui.MyApp
import com.shabeer.al_qurantelugu.presantation.viewmodels.QuranViewModel
import com.shabeer.al_qurantelugu.ui.theme.TeluguquranTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var inAppUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE
    private val REQUEST_CODE_UPDATE = 100
    private var appOpenAd: AppOpenAd? = null

    lateinit var quranViewModel: QuranViewModel
    private var isDialogShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inAppUpdateManager = AppUpdateManagerFactory.create(this)
        if (updateType == AppUpdateType.FLEXIBLE) {
            inAppUpdateManager.registerListener(installStateUpdatedListener)
        }
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)
        checkForAppUpdates()

        setContent {
            TeluguquranTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    MyApp(this)
                }
            }
        }

    }

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                applicationContext,
                "Download Completed, Restarting App in 5 Seconds",
                Toast.LENGTH_SHORT
            ).show()
            lifecycleScope.launch {
                delay(5000)
                inAppUpdateManager.completeUpdate()
            }
        }
    }

    private fun checkForAppUpdates() {
        inAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            val isUpdateAvailable =
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateSupported = when (updateType) {
                AppUpdateType.FLEXIBLE -> appUpdateInfo.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> appUpdateInfo.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable && isUpdateSupported) {
                inAppUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    updateType,
                    this,
                    REQUEST_CODE_UPDATE
                )
            }
        }.addOnFailureListener { exception ->
            Log.e("AppUpdate", "Update check failed", exception)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAdDisplayed) {
            // Do not show the ad if it's already displayed
            return
        }
        appOpenAd?.let {
            it.show(this)
        } ?: run {
            // If the ad is null, load it again
            loadAppOpenAd()
        }
        inAppUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                inAppUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    updateType,
                    this,
                    REQUEST_CODE_UPDATE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(applicationContext, "Update Failed", Toast.LENGTH_SHORT).show()
                Log.d("UpdateError", "Update flow failed with result code $resultCode")
                data?.let {
                    Log.d("UpdateError", "Intent data: ${it.extras}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE) {
            inAppUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }


    private var isAdDisplayed: Boolean = false
    private val appOpenAdLoadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
        override fun onAdLoaded(ad: AppOpenAd) {
            appOpenAd = ad // Initialize the appOpenAd property here
            appOpenAd!!.show(this@MainActivity)
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            // Handle ad loading failure
        }
    }

    private fun loadAppOpenAd() {
        if (AdMobAdUnits.areAdsEnabled) {
            val adRequest = AdRequest.Builder().build()
            AppOpenAd.load(
                this,
                AdMobAdUnits.APP_OPEN_AD_UNIT_ID,
                adRequest,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                appOpenAdLoadCallback
            )
        }

    }

    override fun onBackPressed() {
        if (!isDialogShown) {
            quranViewModel.showDialog()
            isDialogShown = true
        } else {
            super.onBackPressed()
        }
    }
}

