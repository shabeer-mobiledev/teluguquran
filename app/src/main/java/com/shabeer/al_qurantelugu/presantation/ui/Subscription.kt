import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.shabeer.al_qurantelugu.R
import com.shabeer.al_qurantelugu.ads.AdMobAdUnits
import com.shabeer.al_qurantelugu.presantation.ui.onPrimaryColor
import com.shabeer.al_qurantelugu.presantation.ui.primaryColor
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp


@Composable
fun SubscriptionScreen(activity: Activity, navController: NavController, billingViewModel: BillingViewModel = viewModel()) {
    val shouldNavigateToHome by billingViewModel.shouldNavigateToHome.observeAsState(false)

    // Initialize BillingClient
    billingViewModel.initializeBillingClient(activity)

    // UI for subscription options
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ssbgone),
            contentDescription = null,
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop, alpha = .3f
        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.sdp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.sdp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoquran),
                    contentDescription = null,
                    Modifier.size(70.sdp)
                )
                Text(
                    text = "No Ads For Premium Version",
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor()
                )
                Text(
                    text = "Faster Connection",
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor()
                )
                Text(
                    text = "Cancel Anytime",
                    fontSize = 10.ssp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor()
                )
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(onPrimaryColor())
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { billingViewModel.querySubscriptionPlans("teluguquran_onemonth", activity) },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonColors(
                        containerColor = primaryColor(),
                        contentColor = onPrimaryColor(),
                        disabledContainerColor = primaryColor(),
                        disabledContentColor = onPrimaryColor()
                    ),
                    contentPadding = PaddingValues(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.sdp)
                ) {
                    Text("1 Month - ₹99/- Only")
                }
                Spacer(modifier = Modifier.padding(top = 6.sdp))
                Button(
                    onClick = { billingViewModel.querySubscriptionPlans("teluguquran_threemonths", activity) },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonColors(
                        containerColor = primaryColor(),
                        contentColor = onPrimaryColor(),
                        disabledContainerColor = primaryColor(),
                        disabledContentColor = onPrimaryColor()
                    ),
                    contentPadding = PaddingValues(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.sdp)
                ) {
                    Text("3 Months - ₹199/- only (35% Off)")
                }
                Spacer(modifier = Modifier.padding(top = 6.sdp))
                Button(
                    onClick = { billingViewModel.querySubscriptionPlans("teluguquran_oneyear", activity) },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonColors(
                        containerColor = primaryColor(),
                        contentColor = onPrimaryColor(),
                        disabledContainerColor = primaryColor(),
                        disabledContentColor = onPrimaryColor()
                    ),
                    contentPadding = PaddingValues(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.sdp)
                ) {
                    Text("1 Year - ₹399/- only (75% Off)")
                }
                Spacer(modifier = Modifier.padding(top = 6.sdp))
                Button(
                    onClick = { billingViewModel.restorePurchases() },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonColors(
                        containerColor = primaryColor(),
                        contentColor = onPrimaryColor(),
                        disabledContainerColor = primaryColor(),
                        disabledContentColor = onPrimaryColor()
                    ),
                    contentPadding = PaddingValues(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.sdp)
                ) {
                    Text("Restore Purchases")
                }
            }
        }
    }

    // Navigate to home screen when payment is successful or subscription is restored
    if (shouldNavigateToHome) {
        LaunchedEffect(Unit) {
            navController.navigate("home") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}

class BillingViewModel : ViewModel() {
    private val _shouldNavigateToHome = MutableLiveData(false)
    val shouldNavigateToHome: LiveData<Boolean> get() = _shouldNavigateToHome
    var onSubscriptionSuccess: (() -> Unit)? = null

    private var billingClient: BillingClient? = null

    fun initializeBillingClient(activity: Activity) {
        billingClient = BillingClient.newBuilder(activity)
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase) {
                            onSubscriptionSuccess?.invoke()
                        }
                    }
                } else {
                    Log.d("BillingError", "onPurchasesUpdated: ${billingResult.debugMessage}")
                }
            }
            .enablePendingPurchases()
            .build()

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Log.d("BillingError", "onBillingServiceDisconnected")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("BillingSuccess", "onBillingSetupFinished")
                    checkActiveSubscription()
                }
            }
        })
    }

    fun querySubscriptionPlans(subscriptionPlanId: String, activity: Activity) {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(subscriptionPlanId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            )
            .build()

        billingClient?.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val productDetails = productDetailsList.firstOrNull()
                productDetails?.let {
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(it)
                            .setOfferToken(it.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: "")
                            .build()
                    )

                    val billingFlowParams = BillingFlowParams.newBuilder()
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()

                    billingClient?.launchBillingFlow(activity, billingFlowParams)
                }
            }
        }
    }

    fun restorePurchases() {
        billingClient?.queryPurchaseHistoryAsync(BillingClient.ProductType.SUBS) { billingResult, purchaseHistoryRecordList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchaseHistoryRecordList != null) {
                for (purchaseHistoryRecord in purchaseHistoryRecordList) {
                    val purchase = Purchase(
                        purchaseHistoryRecord.originalJson,
                        purchaseHistoryRecord.signature
                    )
                    handlePurchase(purchase) {
                        AdMobAdUnits.areAdsEnabled = false
                        onSubscriptionSuccess?.invoke()
                    }
                }
            } else {
                Log.d("RestoreError", "Error restoring purchases: ${billingResult.debugMessage}")
            }
        }
    }

    private fun handlePurchase(purchase: Purchase, onPurchaseSuccess: () -> Unit) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams
                    .newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        Log.d("PurchaseSuccess", "Purchase acknowledged")
                        onPurchaseSuccess()
                    }
                }
            } else {
                onPurchaseSuccess()
            }
        }
    }

    private fun checkActiveSubscription() {
        val queryPurchaseParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        billingClient?.queryPurchasesAsync(queryPurchaseParams) { result, purchases ->
            when (result.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    val hasActiveSubscription = purchases.any {
                        it.purchaseState == Purchase.PurchaseState.PURCHASED
                    }
                    if (hasActiveSubscription) {
                        AdMobAdUnits.areAdsEnabled = false
                        Log.d("SubscriptionStatus", "User has an active subscription")
                    } else {
                        AdMobAdUnits.areAdsEnabled = true
                        Log.d("SubscriptionStatus", "User does not have an active subscription")
                    }
                }
                else -> {
                    Log.d("SubscriptionError", "Error checking subscription status")
                }
            }
        }
    }
}
