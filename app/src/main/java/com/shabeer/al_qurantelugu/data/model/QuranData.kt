package com.shabeer.al_qurantelugu.data.model

import androidx.annotation.Keep

@Keep
data class QuranData(
    val id : Int? = null,
    val surahnames : String,
    val ruku : String,
    val ayathe : String,
    val pdfurl : String
)
