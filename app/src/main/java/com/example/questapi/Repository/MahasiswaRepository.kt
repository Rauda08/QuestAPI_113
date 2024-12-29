package com.example.questapi.Repository

import com.example.questapi.model.Mahasiswa
import com.example.questapi.service.MahasiswaService
import java.io.IOException

interface MahasiswaRepository {
    suspend fun getMahasiswa(): List<Mahasiswa>
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
    suspend fun updateMahasiswa (nim: String, mahasiswa: Mahasiswa)
    suspend fun deleteMahasiswa (nim: String)
    suspend fun getMahasiswaById(nim: String): Mahasiswa
}



