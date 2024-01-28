package com.example.mydemoapplication.screens.mylist

sealed interface ListEvent {
    data class SortCharacters(val sortType: SortType): ListEvent
}