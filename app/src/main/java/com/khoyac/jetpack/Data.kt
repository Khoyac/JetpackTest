package com.khoyac.jetpack

data class Data (
    val id: Int,
    val datos: Datos
)

data class Datos (
    val picture: String,
    val title: String,
    val desc: String
        )