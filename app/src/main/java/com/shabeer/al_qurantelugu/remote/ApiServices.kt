package com.shabeer.al_qurantelugu.remote

import com.shabeer.al_qurantelugu.data.model.PagerData
import com.shabeer.al_qurantelugu.data.model.QuranData
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("fetchpager.php")
    suspend fun getPagerData(): Response<List<PagerData>>


    @GET("surahnames.php")
    suspend fun surahData(): Response<List<QuranData>>

}



