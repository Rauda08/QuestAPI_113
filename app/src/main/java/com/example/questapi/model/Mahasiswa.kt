package com.example.questapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mahasiswa (
    val nim : String,
    val nama : String,
    val alamat : String,
    val kelas : String,
    val angkatan : String,

    @SerialName("Jenis kelamin")
    val jeniskelamin: String
)