package com.example.mydemoapplication.mylist


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydemoapplication.data.remote.respones.Result
import com.example.mydemoapplication.repository.MyRepository
import com.example.mydemoapplication.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    var characterList = mutableStateOf<List<ListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getCharacterList()) {
                is Resource.Success -> {
                    val entries = result.data?.results?.mapIndexed { index, entry ->
                        ListEntry(entry.name)
                    }
                    if (entries != null) {
                        characterList.value = entries
                        println(characterList.value )
                    }
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}

data class ListEntry(
    val name: String
)
