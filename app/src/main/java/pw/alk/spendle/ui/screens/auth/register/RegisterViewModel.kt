package pw.alk.spendle.ui.screens.auth.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pw.alk.spendle.models.RegisterRequest
import pw.alk.spendle.network.RetrofitBuilder.apiService
import pw.alk.spendle.ui.shared.BaseViewModel
import pw.alk.spendle.ui.shared.Event
import pw.alk.spendle.ui.utils.TokenDataStore
import pw.alk.spendle.ui.utils.getErrMessageFromBody
import java.io.IOException
import javax.inject.Inject

sealed interface RegisterEvent : Event {
    object RegisterSuccess : RegisterEvent
    data class RegisterFailure(val message: String) : RegisterEvent
}

@HiltViewModel
class RegisterViewModel @Inject constructor(private val tokenDataStore: TokenDataStore) :
    BaseViewModel<RegisterEvent>() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val req = RegisterRequest(email, password)
            _isLoading.value = true
            try {
                val res = apiService.registerUser(req)

                if (res.isSuccessful) {
                    tokenDataStore.saveToken(res.body()!!.token)
                    sendEvent(RegisterEvent.RegisterSuccess)
                } else {
                    if (res.code() == 409) {
                        sendEvent(RegisterEvent.RegisterFailure("User with email already exists!"))
                        return@launch
                    }

                    sendEvent(RegisterEvent.RegisterFailure(getErrMessageFromBody(res.errorBody()!!)))
                }
            } catch (e: IOException) {
                sendEvent(RegisterEvent.RegisterFailure("Internet issue?!"))
            } catch (e: Exception) {
                sendEvent(
                    RegisterEvent.RegisterFailure(
                        e.localizedMessage ?: "Something went wrong!"
                    )
                )
            }
            _isLoading.value = false
        }
    }
}
