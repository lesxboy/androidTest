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
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MyRepository,
) : ViewModel() {

    data class UiState(
        val loadError: String = "",
        val isLoading: Boolean = false,
        var characterList: List<CharResult> = listOf(),
        val sortType: SortType = SortType.NAME,
        val searchText: String = "",
    )

    private val _uiState = MutableStateFlow(UiState())
    private var _characterList = mutableStateOf(_uiState.value.characterList)
    private val _sortType = MutableStateFlow(_uiState.value.sortType)
    private val _characters = MutableStateFlow(_characterList.value)
    private val _isLoading = MutableStateFlow(_uiState.value.isLoading)
    private var _loadError = MutableStateFlow(_uiState.value.loadError)

    val searchText = MutableStateFlow(_uiState.value.searchText)
    private val _filteredList = searchText
        .debounce(500L)
        .onEach { _isLoading.update { true } }
        .combine(_characters) { text, _ ->
            if(text.isBlank()) {
                _characters.value
            } else {
                _characters.value.filter {
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
    val uiState = combine(_uiState, _sortType, _filteredList, _isLoading, _loadError) { state, sortType, filteredList, isLoading, loadError ->
        state.copy(
            characterList = filteredList,
            sortType = sortType,
            isLoading = isLoading,
            loadError = loadError
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    init {
        fetchCharacters()
    }

    fun onEvent(event: ListEvent) {
        when(event) {
            is ListEvent.SortCharacters -> {
                updateSort(event.sortType)
            }
        }
    }

    fun onSearchTextChange(text: String) {
       searchText.value = text
    }

    fun getCharacter(id: String): CharResult? {
        return _characters.value.find { it.id.toString() == id } ?: throw IOException("Get Character failed")
    }

    private fun updateSort(sortType: SortType) {
        val sortList: List<CharResult> = _characters.value
        _sortType.value = sortType
        
        when (sortType) {
           SortType.SPECIES ->_characters.value = sortList.sortedBy { myObject -> myObject.species }
           SortType.NAME ->  _characters.value = sortList.sortedBy { myObject -> myObject.name }
           SortType.STATUS -> _characters.value = sortList.sortedBy { myObject -> myObject.status }
       }
    }

    private fun fetchCharacters() {
        _isLoading.value = true
        viewModelScope.launch {
            when(val result = repository.getCharacterList()) {
                is Resource.Success -> {
                    val entries = result.data?.results

                    if (entries != null) {
                        _characters.value = entries
                    }

                    updateSort(_sortType.value)
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    //todo show error message
                    _loadError.value = result.message!!
                    _isLoading.value = false
                }
                else -> {
                    _isLoading.value = false
                }
            }
        }
    }
}