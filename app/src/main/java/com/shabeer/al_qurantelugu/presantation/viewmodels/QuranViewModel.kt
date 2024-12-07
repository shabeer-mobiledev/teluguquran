package com.shabeer.al_qurantelugu.presantation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabeer.al_qurantelugu.data.model.QuranData
import com.shabeer.al_qurantelugu.data.repositories.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(private val quranRepository: QuranRepository) :
    ViewModel() {

    private val _isDialogVisible = MutableLiveData<Boolean>(false) // Default to false
    var isDailogue : LiveData<Boolean> = _isDialogVisible


    private val _quranData = MutableLiveData<List<QuranData>>()
    val pagerData: LiveData<List<QuranData>> = _quranData

    init {
        fetchSurahNames()
    }

    fun fetchSurahNames() {
        viewModelScope.launch {
            try {
                val response = quranRepository.getSurahNames()
                if (response.isSuccessful) {
                    _quranData.value = response.body()
                    Log.d("sucess", _quranData.value.toString())
                } else {
                    Log.d("no response", response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("sucess", e.message.toString())
            }
        }
    }

    fun showDialog() {
        _isDialogVisible.value = true
    }

    fun dismissDialog() {
        _isDialogVisible.value = false
    }
}

