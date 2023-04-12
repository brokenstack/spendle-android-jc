package pw.alk.spendle.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pw.alk.spendle.models.AuthRequest
import pw.alk.spendle.network.RetrofitBuilder
import pw.alk.spendle.ui.shared.BaseViewModel
import pw.alk.spendle.ui.shared.Event
import pw.alk.spendle.ui.utils.TokenDataStore
import pw.alk.spendle.ui.utils.getErrMessageFromBody
import java.io.IOException
import javax.inject.Inject

sealed interface LoginEvent : Event {
    object LoginSuccess : LoginEvent
    data class LoginFailure(val message: String) : LoginEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val tokenDataStore: TokenDataStore) :
    BaseViewModel<LoginEvent>() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val req = AuthRequest(email, password)
            _isLoading.value = true
            try {
                val res = RetrofitBuilder.apiService.loginUser(req)

                if (res.isSuccessful) {
                    tokenDataStore.saveToken(res.body()!!.token)
                    sendEvent(LoginEvent.LoginSuccess)
                } else {
                    if (res.code() == 403) {
                        sendEvent(LoginEvent.LoginFailure("Invalid username or password!"))
                        return@launch
                    }

                    sendEvent(LoginEvent.LoginFailure(getErrMessageFromBody(res.errorBody()!!)))
                }
            } catch (e: IOException) {
                sendEvent(LoginEvent.LoginFailure("Internet issue?!"))
            } catch (e: Exception) {
                sendEvent(
                    LoginEvent.LoginFailure(
                        e.localizedMessage ?: "Something went wrong!"
                    )
                )
            }
            _isLoading.value = false
        }
    }
}
