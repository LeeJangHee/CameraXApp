package com.example.cameraxapp.model

import java.io.File

data class Picture(
    val path: String,
    val date: String,
    val index: Int,
    val file: File
)
