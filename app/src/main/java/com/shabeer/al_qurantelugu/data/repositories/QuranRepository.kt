package com.shabeer.al_qurantelugu.data.repositories

import com.shabeer.al_qurantelugu.remote.ApiServices
import javax.inject.Inject

class QuranRepository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getSurahNames() = apiServices.surahData()
}