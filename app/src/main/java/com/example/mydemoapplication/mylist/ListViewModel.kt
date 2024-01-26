package com.example.mydemoapplication.mylist


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import com.example.mydemoapplication.repository.MyRepository
import com.example.mydemoapplication.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    var characterList = mutableStateOf<List<ListEntry>>(listOf())
    var loadError = mutableStateOf("")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _characters = MutableStateFlow(characterList.value)
    val persons = searchText
        .debounce(500L)
        .onEach { _isLoading.update { true } }
        .combine(_characters) { text, entry ->
            if(text.isBlank()) {
                characterList.value
            } else {
                characterList.value.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isLoading.update {
            false
        } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _characters.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            when(val result = repository.getCharacterList()) {
                is Resource.Success -> {
                    val entries = result.data?.results?.mapIndexed { index, entry ->
                        ListEntry(entry.name)
                    }
                    if (entries != null) {
                        characterList.value = entries
                    }
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    _isLoading.value = false
                }
            }
        }
    }
}

data class ListEntry(
    val name: String){
    fun doesMatchSearchQuery(query: String): Boolean {
        return name.toString().any {
            it.lowercase().contains(query.lowercase(), ignoreCase = true)
        }
    }
}