package com.trkpo.taskmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trkpo.taskmanager.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginByLinkViewModel @Inject constructor(
    val auth: AuthRepository,
) : ViewModel() {
    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()
    fun login(link: String, password: String) {
        viewModelScope.launch {
            try {
                _uiState.send(UiState.Waiting)
                auth.loginByLink(link, password)
                _uiState.send(UiState.Done)
            } catch (e: Exception) {
                _uiState.send(UiState.Init)
            }
        }
    }
}

enum class UiState {
    Init, Waiting, Done
}