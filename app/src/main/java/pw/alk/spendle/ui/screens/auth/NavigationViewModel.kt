package pw.alk.spendle.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pw.alk.spendle.ui.utils.TokenDataStore
import javax.inject.Inject

sealed class ViewState {
    object Loading : ViewState()
    object LoggedIn : ViewState()
    object NotLoggedIn : ViewState()
}

@HiltViewModel
class NavigationViewModel @Inject constructor(private val tokenDataStore: TokenDataStore) :
    ViewModel() {
    private val _loadState = MutableStateFlow<ViewState>(ViewState.Loading)
    val loadState = _loadState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenDataStore.getToken()
            if (token != null) {
                _loadState.value = ViewState.LoggedIn
            } else {
                _loadState.value = ViewState.NotLoggedIn
            }
        }
    }
}