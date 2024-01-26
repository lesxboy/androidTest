package com.example.mydemoapplication.data.remote.respones

data class CharResult(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
){
    fun doesMatchSearchQuery(query: String): Boolean {
        return name.lowercase().contains(query.lowercase())
    }
}