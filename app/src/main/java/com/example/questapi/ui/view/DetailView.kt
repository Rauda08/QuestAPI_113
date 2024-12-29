package com.example.questapi.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.questapi.ui.customwidget.CostumeTopAppBar
import com.example.questapi.ui.navigation.DestinasiNavigasi
import com.example.questapi.ui.viewmodel.DetailViewModel
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questapi.ui.viewmodel.DetailUiState


object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "detail Mhs"
    const val nim = "nim"
    val routeWithArgs = "$route/{$nim}"
}
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMahasiswaView(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Scaffold (
        topBar = {
            CostumeTopAppBar(
                title = "Detail Mahasiswa",
                canNavigateBack = true,
                navigateUp = onBack,
                onRefresh = {
                    viewModel.getMahasiswaDetail(viewModel.detailUiState.value.detailUiEvent.nim)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.nim)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa",
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMahasiswa(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState
        )
    }
}

@Composable
fun BodyDetailMahasiswa(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailUiState.isError -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Terjadi Kesalahan: ${detailUiState.errorMessage}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    ItemDetailMahasiswa(
                        label = "NIM",
                        value = detailUiState.detailUiEvent.nim,
                        icon = Icons.Default.AccountBox
                    )
                    ItemDetailMahasiswa(
                        label = "Nama",
                        value = detailUiState.detailUiEvent.nama,
                        icon = Icons.Default.Person
                    )
                    ItemDetailMahasiswa(
                        label = "Alamat",
                        value = detailUiState.detailUiEvent.alamat,
                        icon = Icons.Default.Home
                    )
                    ItemDetailMahasiswa(
                        label = "Jenis Kelamin",
                        value = detailUiState.detailUiEvent.jenisKelamin,
                        icon = Icons.Default.MailOutline
                    )
                    ItemDetailMahasiswa(
                        label = "Kelas",
                        value = detailUiState.detailUiEvent.kelas,
                        icon = Icons.Default.Warning
                    )
                    ItemDetailMahasiswa(
                        label = "Angkatan",
                        value = detailUiState.detailUiEvent.angkatan,
                        icon = Icons.Default.Warning
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { deleteConfirmationRequired = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Hapus Mahasiswa")
                    }
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Mahasiswa Tidak Ditemukan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ItemDetailMahasiswa(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "$label:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}