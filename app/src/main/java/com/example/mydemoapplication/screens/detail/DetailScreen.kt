package com.example.mydemoapplication.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mydemoapplication.screens.mylist.ListViewModel

@Composable
fun DetailScreen(navController: NavController, id: String) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel: ListViewModel = hiltViewModel()
        val character = viewModel.getCharacter(id)

        Scaffold( topBar = {  TopAppBar( title = { if (character != null) { Text(text = character.name) } },
                    navigationIcon = if (navController.previousBackStackEntry != null) {
                        {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "CHAR INFO"
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            },
            content = {
                        Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(20.dp),
                   horizontalAlignment = Alignment.CenterHorizontally

               ) { if (character != null) {
                        Image(painter = rememberAsyncImagePainter(character.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                   .size(270.dp)
                                   .clip(CircleShape)
                                   .border(2.dp, Color.Gray, CircleShape)
                           )
                            Text(text = "Name: ${character.name}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Text(text = "Gender: ${character.gender}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Text(text = "Status: ${character.status}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Text(text = "Origin: ${character.origin.name}", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        )
    }
}
