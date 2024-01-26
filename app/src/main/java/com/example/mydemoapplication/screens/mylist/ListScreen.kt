package com.example.mydemoapplication.screens.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mydemoapplication.data.remote.respones.CharResult
import kotlinx.coroutines.flow.collect

@Composable
fun ListScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar( title = { Text(text ="Ricky and Morty") }) }
    ) {
        ViewBody(navController)
    }
}

@Composable
fun ViewBody(navController: NavController) {

    fun navigateToDetailScreen(char: CharResult) {
        navController.navigate("DETAIL/${char.id}")
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: ListViewModel = hiltViewModel()
        val searchText by viewModel.searchText.collectAsState()
        val characters by viewModel.characters.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(characters) { character ->
                        CharacterRow(character = character) { charResult ->
                            navigateToDetailScreen(charResult)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterRow(character: CharResult, onItemClick: (CharResult) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onItemClick(character) }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = character.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}