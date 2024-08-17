package com.example.tinderfordogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinderfordogs.model.TinderCardModel
import com.example.tinderfordogs.repository.FavoriteDogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDogsViewModel @Inject constructor(private val repository: FavoriteDogsRepository):ViewModel() {

    val _dogList = MutableStateFlow<List<TinderCardModel>>(emptyList())
    val dogList = _dogList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDogs().distinctUntilChanged().collect{listOfNotes->
                if(listOfNotes.isNotEmpty()){
                    _dogList.value=listOfNotes
                }
            }
        }
    }


    fun removeDog(dog:TinderCardModel)=viewModelScope.launch { repository.deleteDog(dog) }


}