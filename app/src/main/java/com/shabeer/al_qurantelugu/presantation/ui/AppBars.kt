package com.shabeer.al_qurantelugu.presantation.ui

import SubscriptionScreen
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shabeer.al_qurantelugu.R
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits
import com.shabeer.al_qurantelugu.data.model.NavigationItemsData
import com.shabeer.al_qurantelugu.presantation.viewmodels.QuranViewModel
import com.shabeer.al_qurantelugu.presantion.screens.AboutUsScreen
import com.shabeer.al_qurantelugu.presantion.screens.PrivacyPolicy
import com.shabeer.al_qurantelugu.util.Constents
import kotlinx.coroutines.launch
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

val navigationItemList = listOf<NavigationItemsData>(
    NavigationItemsData(
        itemName = "Home",
        iconName = Icons.Default.Home
    ),
    NavigationItemsData(
        itemName = "Privacy Policy",
        iconName = Icons.Default.Email
    ),
    NavigationItemsData(
        itemName = "Share App",
        iconName = Icons.Default.Share
    ),
    NavigationItemsData(
        itemName = "Rate Us",
        iconName = Icons.Default.Star
    ),
    NavigationItemsData(
        itemName = "About Us",
        iconName = Icons.Default.Info
    ),
    NavigationItemsData(
        itemName = "Exit App",
        iconName = Icons.Default.ExitToApp
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScreen(
    title: String,
    navigationIcon: Int,
    actionIcon: Int,
    activity: Activity,
    quranViewModel: QuranViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    val navHostController = rememberNavController()
    val context = LocalContext.current

    val isDialogVisible by quranViewModel.isDailogue.observeAsState(initial = false)

    ModalNavigationDrawer(drawerState = drawerState, gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(drawerTonalElevation = DrawerDefaults.ModalDrawerElevation) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.sdp), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ssbgone),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logoquran),
                            contentDescription = null,
                            Modifier
                                .size(80.sdp)
                                .padding(top = 15.sdp, end = 15.sdp)
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 20.ssp,
                            fontFamily = FontFamily(Font(R.font.ramabhadra)),
                            color = primaryColor()
                        )
                    }
                }
                Divider()

                Spacer(modifier = Modifier.padding(top = 10.sdp))
                navigationItemList.forEachIndexed { index, navigationItemsData ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(start = 10.sdp, end = 10.sdp),
                        label = { Text(text = navigationItemsData.itemName) },
                        selected = index == selectedIndex.value,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = onPrimaryColor(),
                            selectedIconColor = onPrimaryColor(),
                            selectedContainerColor = primaryColor(),
                            unselectedIconColor = primaryColor(),
                            unselectedTextColor = primaryColor()
                        ),
                        icon = {
                            Icon(
                                imageVector = navigationItemsData.iconName,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            scope.launch {
                                when (navigationItemsData.itemName) {
                                    "Home" -> {
                                        navHostController.navigate("home") {
                                            this.popUpTo("home")
                                        }
                                    }

                                    "Privacy Policy" -> {
                                        navHostController.navigate("Privacy Policy") {
                                            this.popUpTo("home")
                                        }
                                    }

                                    "Share App" -> {
                                        shareApp(context)
                                    }

                                    "Rate Us" -> {
                                        rateUs(context)
                                    }

                                    "About Us" -> {
                                        navHostController.navigate("About Us") {
                                            this.popUpTo("home")
                                        }
                                    }

                                    "Exit App" -> {
                                        quranViewModel.showDialog()
                                    }
                                }
                                selectedIndex.value = index
                                drawerState.close()
                            }
                        })
                }

                // version name
                Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
                    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                    Text(
                        "Version : " + packageInfo.versionName,
                        color = primaryColor(),
                        fontSize = 9.ssp,
                        modifier = Modifier.padding(bottom = 5.sdp)
                    )
                }
            }
        }, content = {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            fontFamily = FontFamily(Font(R.font.regular)),
                            modifier = Modifier.padding(top = 10.sdp)
                        )
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(navigationIcon),
                            contentDescription = null,
                            Modifier
                                .padding(start = 10.sdp)
                                .size(20.sdp)
                                .clickable { scope.launch { drawerState.open() } },
                            colorFilter = ColorFilter.tint(primaryColor())
                        )
                    }, actions = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.size(60.sdp, 40.sdp).clickable {
                                if (AdMobAdUnits.areAdsEnabled) {
                                    navHostController.navigate("premium") {
                                        this.popUpTo("home")
                                    }
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "You are a Premium User",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }) {
                            Image(
                                painter = painterResource(actionIcon),
                                contentDescription = null,
                                Modifier
                                    .size(20.sdp)
                            )
                            Text("Remove Ads", fontSize = 6.ssp, textAlign = TextAlign.Center)
                        }

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        titleContentColor = primaryColor(),
                    )
                )
            }) {
                Box(Modifier.padding(it)) {
                    NavHost(navController = navHostController, startDestination = "home") {
                        composable("home") {
                            HomeScreen()
                        }
                        composable("Privacy Policy") {
                            PrivacyPolicy()
                        }
                        composable("About Us") {
                            AboutUsScreen()
                        }
                        composable("premium") {
                            SubscriptionScreen(activity, navHostController)
                        }
                    }

                    if (isDialogVisible) {
                        context as Activity
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            AlertDialog({ quranViewModel.dismissDialog() },
                                { context.finish() },
                                { rateUs(context) })
                        }

                    }
                }
            }
        })
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var isRefrshing by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        if (Constents.isInternetAvailable(context = context)) {
            Column {
                PagerView()
                SurahScreen()
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Please check your internet connection.")
                    Spacer(modifier = Modifier.height(5.sdp))
                    Button(onClick = {
                        isRefrshing = true
                    }, colors = ButtonDefaults.buttonColors(primaryColor())) {
                        Text("Refresh")
                    }
                }
            }

            if (isRefrshing) {
                if (Constents.isInternetAvailable(context = context)) {
                    Column {
                        PagerView()
                        SurahScreen()
                    }
                } else {
                    isRefrshing = false
                    Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}

fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out this app")
        putExtra(
            Intent.EXTRA_TEXT,
            "Hey, check out this cool app: https://play.google.com/store/apps/details?id=$appPackageName"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share App via"))
}

fun rateUs(context: Context) {
    val appPackageName = context.packageName // Get current package name
    try {
        // Try to open the Google Play Store app
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (e: ActivityNotFoundException) {
        // If the Play Store app is not installed, open the Play Store in the browser
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}
