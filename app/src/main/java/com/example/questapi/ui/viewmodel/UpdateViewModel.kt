package com.example.questapi.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi.Repository.MahasiswaRepository
import com.example.questapi.model.Mahasiswa
import kotlinx.coroutines.launch


class UpdateViewModel(private val repository: MahasiswaRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateUiState())
        private set

    fun loadMahasiswaData(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = repository.getMahasiswaById(nim)
                uiState = UpdateUiState(updateUiEvent = mahasiswa.toUpdateUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUiEvent(updateUiEvent: UpdateUiEvent) {
        uiState = UpdateUiState(updateUiEvent = updateUiEvent)
    }

    fun updateMhs() {
        viewModelScope.launch {
            try {
                repository.updateMahasiswa(uiState.updateUiEvent.nim, uiState.updateUiEvent.toMhs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateUiState(
    val updateUiEvent: UpdateUiEvent = UpdateUiEvent()
)

data class UpdateUiEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// Fungsi untuk mengonversi UpdateUiEvent ke Mahasiswa
fun UpdateUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jeniskelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)

// Fungsi untuk mengonversi Mahasiswa ke UpdateUiEvent
fun Mahasiswa.toUpdateUiEvent(): UpdateUiEvent = UpdateUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jeniskelamin,
    kelas = kelas,
    angkatan = angkatan
)