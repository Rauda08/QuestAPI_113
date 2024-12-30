package com.example.questapi.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi.Repository.MahasiswaRepository
import com.example.questapi.model.Mahasiswa
import com.example.questapi.ui.view.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailMhsUiState{
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {

    private val nim: String = checkNotNull(savedStateHandle[DestinasiDetail.nim])
    var detailMhsUiState: DetailMhsUiState by mutableStateOf(DetailMhsUiState.Loading)
        private set

    init {
        getMhsbyNim()
    }

    fun getMhsbyNim() {
        viewModelScope.launch {
            detailMhsUiState = DetailMhsUiState.Loading
            detailMhsUiState = try {
                DetailMhsUiState.Success(mahasiswa = mahasiswaRepository.getMahasiswaByNim(nim))
            } catch (e: IOException) {
                DetailMhsUiState.Error
            }
        }
    }
}
