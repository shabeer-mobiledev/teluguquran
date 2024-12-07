package com.shabeer.al_qurantelugu.presantation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabeer.al_qurantelugu.data.model.PagerData
import com.shabeer.al_qurantelugu.data.repositories.PagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagerDataViewModel @Inject constructor(private val repository: PagerRepository) :
    ViewModel() {

    private val _pagerData = MutableLiveData<List<PagerData>>()
    val pagerData: LiveData<List<PagerData>> = _pagerData

    init {
        fetchPagerImages()
    }

   fun fetchPagerImages() {
        viewModelScope.launch {
            try {
                val response = repository.getPagerData()
                if (response.isSuccessful) {
                    _pagerData.value = response.body()

                } else {
                  // if error show
                }
            } catch (e: Exception) {
                Log.d("sucess", e.message.toString())
            }
        }
    }
}