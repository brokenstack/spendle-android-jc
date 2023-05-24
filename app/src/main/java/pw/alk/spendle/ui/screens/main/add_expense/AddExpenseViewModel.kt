package pw.alk.spendle.ui.screens.main.add_expense

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.alk.spendle.models.TransactionBody
import pw.alk.spendle.network.RetrofitBuilder
import pw.alk.spendle.ui.shared.BaseViewModel
import pw.alk.spendle.ui.shared.Event
import pw.alk.spendle.ui.utils.TokenDataStore
import pw.alk.spendle.ui.utils.getErrMessageFromBody
import java.io.IOException
import javax.inject.Inject

sealed interface RequestEvent : Event {
    object Success : RequestEvent
    data class Failure(val message: String) : RequestEvent
}

@HiltViewModel
class AddExpenseViewModel @Inject constructor(private val tokenDataStore: TokenDataStore) :
    BaseViewModel<RequestEvent>() {
    fun makeAddExpenseRequest(
        title: String?, type: String, value: Int, timestamp: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val body = TransactionBody(title, type, value, timestamp)
            try {
                val res =
                    RetrofitBuilder.apiService.newTransaction(body, tokenDataStore.getToken()!!)
                if (res.isSuccessful) {
                    sendEvent(RequestEvent.Success)
                } else {
                    sendEvent(RequestEvent.Failure(getErrMessageFromBody(res.errorBody()!!)))
                }
            } catch (e: IOException) {
                sendEvent(RequestEvent.Failure("Internet issue?!"))
            } catch (e: Exception) {
                sendEvent(
                    RequestEvent.Failure(
                        e.localizedMessage ?: "Something went wrong!"
                    )
                )
            }
        }
    }
}