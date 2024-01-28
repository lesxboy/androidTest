package com.example.mydemoapplication.screens.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mydemoapplication.data.remote.respones.CharResult

@Composable
fun ListScreen(navController: NavController) {
    val viewModel: ListViewModel = hiltViewModel()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold( topBar = { TopAppBar( title = { Text(text ="Ricky and Morty") }) }
   ) {
            fun navigateToDetailScreen(char: CharResult) {
                navController.navigate("DETAIL/${char.id}")
            }
            val state by viewModel.uiState.collectAsState()
            val onEvent = viewModel::onEvent
            val searchText by viewModel.searchText.collectAsState()

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
                if(state.isLoading) {
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
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState()),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SortType.values().forEach { sortType ->
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                onEvent(ListEvent.SortCharacters(sortType))
                                            },
                                        verticalAlignment = CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = state.sortType == sortType,
                                            onClick = {
                                                onEvent(ListEvent.SortCharacters(sortType))
                                            }
                                        )
                                        Text(text = sortType.name)
                                    }
                                }
                            }
                        }

                        items(state.characterList) { character ->
                            CharacterRow(character = character) { charResult ->
                                navigateToDetailScreen(charResult)
                            }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = character.name, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = "Species: ${character.species}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
            Text(text = "Status: ${character.status}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
        }
    }
}