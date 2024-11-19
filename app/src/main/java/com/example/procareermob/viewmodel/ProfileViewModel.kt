package com.example.procareermob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ProfileViewModel : ViewModel() {
    private val storage = FirebaseStorage.getInstance()

    // Функция загрузки аватара в Firebase Storage
    fun uploadAvatar(imageData: ByteArray, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val avatarRef = storage.reference.child("avatars/${UUID.randomUUID()}.jpg")
        viewModelScope.launch {
            try {
                val uploadTask = avatarRef.putBytes(imageData).await()
                val downloadUrl = uploadTask.storage.downloadUrl.await().toString()
                onSuccess(downloadUrl)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}