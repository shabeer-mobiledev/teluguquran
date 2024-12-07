package com.shabeer.al_qurantelugu.data.repositories

import com.shabeer.al_qurantelugu.remote.ApiServices
import javax.inject.Inject

class PagerRepository @Inject constructor(private val apiServices: ApiServices){
    suspend fun getPagerData() = apiServices.getPagerData()
}