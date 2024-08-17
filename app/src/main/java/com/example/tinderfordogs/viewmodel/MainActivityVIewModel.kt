package com.example.tinderfordogs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinderfordogs.repository.FetchDogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.tinderfordogs.model.TinderCardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber


@HiltViewModel
class MainActivityVIewModel@Inject constructor(
    private val repository: FetchDogRepository
):ViewModel()
{
    private val _dogImages = MutableLiveData<MutableList<TinderCardModel>>()
    val dogImages: LiveData<MutableList<TinderCardModel>> get() = _dogImages
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    init {
        _dogImages.value = mutableListOf()
        fetchImages()
    }


    fun fetchImages() {
        _loading.value=true
        try{
            for(i in 0..10){
                viewModelScope.launch {
                    try {
                        val response = repository.getAllDogs()
                        if (response.status == "success") {
                            val newImages = mutableListOf<TinderCardModel>()
                            newImages.add(response)
                            _dogImages.value?.addAll(newImages)
                            _dogImages.value = _dogImages.value
                            Timber.d("$i = ${response.toString()}")
                        }
                        if(i==10){
                            _loading.value=false
                            Timber.d("Loding values $loading")

                        }
                    } catch (e: Exception) {
                        Timber.d(e.message)
                    }
                }
            }


        }catch (e:Exception){
            Timber.d(e.localizedMessage)
        }
    }


    fun addDog(dog:TinderCardModel) = viewModelScope.launch { repository.addDog(dog) }


}