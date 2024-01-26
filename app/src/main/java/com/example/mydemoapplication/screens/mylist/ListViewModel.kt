package com.example.mydemoapplication.screens.mylist
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydemoapplication.data.remote.respones.CharResult
import com.example.mydemoapplication.repository.MyRepository
import com.example.mydemoapplication.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MyRepository,
) : ViewModel() {
    var characterList = mutableStateOf<List<CharResult>>(listOf())
    var loadError = mutableStateOf("")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _characters = MutableStateFlow(characterList.value)
    val characters = searchText
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

    init {
        fetchCharacters()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getCharacter(id: String): CharResult? {
        return characterList.value.find { it.id.toString() == id }
    }

    private fun fetchCharacters() {
        _isLoading.value = true
        viewModelScope.launch {
            when(val result = repository.getCharacterList()) {
                is Resource.Success -> {
                    val entries = result.data?.results

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