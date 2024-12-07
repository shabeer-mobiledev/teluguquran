package com.shabeer.al_qurantelugu

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.shabeer.al_qurantelugu.presantation.ui.PdfScreen
import com.shabeer.al_qurantelugu.ui.theme.TeluguquranTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setContent {
            TeluguquranTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    val pdfUrl = intent.getStringExtra("pdfUrl")
                    Box(Modifier.padding()) {
                        val navHostController = rememberNavController()
                        PdfScreen(
                            navHostController = navHostController,
                            pdfUrl!!,
                            this@DetailActivity,
                            this@DetailActivity
                        )
                    }
                }
            }
        }
    }
}

