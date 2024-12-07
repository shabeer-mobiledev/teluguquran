package com.shabeer.al_qurantelugu.presantation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shabeer.al_qurantelugu.R


@Composable
fun MyApp(activity: Activity) {

    TopAppBarScreen(stringResource(R.string.app_name), R.drawable.menu, R.drawable.premium,activity)
}

