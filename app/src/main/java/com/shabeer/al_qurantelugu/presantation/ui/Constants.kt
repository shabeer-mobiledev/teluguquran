package com.shabeer.al_qurantelugu.presantation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.shabeer.al_qurantelugu.R
import com.shabeer.al_qurantelugu.ui.theme.onPrimaryDark
import com.shabeer.al_qurantelugu.ui.theme.onPrimaryDarkContainer
import com.shabeer.al_qurantelugu.ui.theme.onPrimaryLight
import com.shabeer.al_qurantelugu.ui.theme.onPrimaryLightContainer
import com.shabeer.al_qurantelugu.ui.theme.primaryDark
import com.shabeer.al_qurantelugu.ui.theme.primaryDarkContainer
import com.shabeer.al_qurantelugu.ui.theme.primaryLight
import com.shabeer.al_qurantelugu.ui.theme.primaryLightContainer
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun primaryColor(): Color {
    val color = if (isSystemInDarkTheme()) {
        primaryDark
    } else {
        primaryLight
    }

    return color
}

@Composable
fun primaryContainerColor(): Color {
    val color = if (isSystemInDarkTheme()) {
        primaryDarkContainer
    } else {
        primaryLightContainer
    }

    return color
}

@Composable
fun onPrimaryColor(): Color {
    val color = if (isSystemInDarkTheme()) {
        onPrimaryDark
    } else {
        onPrimaryLight
    }

    return color
}

@Composable
fun onPrimaryContainerColor(): Color {
    val color = if (isSystemInDarkTheme()) {
        onPrimaryDarkContainer
    } else {
        onPrimaryLightContainer
    }

    return color
}


@Composable
fun AlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    onRate: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = onPrimaryColor()),
            modifier = Modifier

                .fillMaxWidth()
                .height(300.sdp),
            shape = MaterialTheme.shapes.large
        ) {
            Box(modifier = Modifier.fillMaxHeight()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.logoquran),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.sdp)
                            .size(45.sdp)
                    )
                    Text("Are you sure you want to exit?", color = primaryColor())

                    Text(
                        "మీకు తెలుగు ఖురాన్ యాప్ ఎలా ఉపయోగపడుతుందో మాకు తెలియజేయండి. మీ రేటింగ్ మరియు అభిప్రాయాలు మాకు అందించిన అనుభవాన్ని మెరుగుపరచడంలో సహాయపడతాయి. మీ సూచనలతో, మేము మరింత శ్రేష్ఠమైన సేవలను అందించగలుగుతాము. మీ అభిప్రాయం ఇతర వినియోగదారులకు కూడా మార్గనిర్దేశం చేస్తుంది.",
                        fontSize = 8.ssp,
                        textAlign = TextAlign.Justify,
                        color = primaryColor(),
                        modifier = Modifier.padding(15.sdp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.sdp)
                    ) {
                        Text("Rate Us", modifier = Modifier.clickable { onRate() }, color = primaryColor())
                        Text("Exit App", modifier = Modifier.clickable { onExit() },
                            primaryColor()
                        )
                    }
                }
            }
        }
    }
}