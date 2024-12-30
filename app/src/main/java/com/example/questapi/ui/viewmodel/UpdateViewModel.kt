package com.example.questapi.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi.Repository.MahasiswaRepository
import com.example.questapi.model.Mahasiswa
import com.example.questapi.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch


class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.nim])

    init {
        viewModelScope.launch {
            uiState = mahasiswaRepository.getMahasiswaByNim(nim).toUiStateMhs()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun UpdateMahasiswa(){
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(nim, uiState.insertUiEvent.toMhs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}