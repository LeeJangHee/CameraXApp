package com.example.cameraxapp.model

import java.io.File

data class PictureModel(
    val path: String,
    val date: String,
    val index: Int,
    val file: File
)
