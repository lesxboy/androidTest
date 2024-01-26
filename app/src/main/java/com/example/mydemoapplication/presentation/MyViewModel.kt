package com.example.mydemoapplication.presentation

import androidx.lifecycle.ViewModel
import com.example.mydemoapplication.repository.MyRepository
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    repository: Lazy<MyRepository>
): ViewModel() {

    init {
        repository.get()
    }
}