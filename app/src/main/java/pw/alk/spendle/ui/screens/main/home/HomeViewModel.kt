package pw.alk.spendle.ui.screens.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pw.alk.spendle.network.RetrofitBuilder
import pw.alk.spendle.ui.utils.TokenDataStore
import pw.alk.spendle.ui.utils.getErrMessageFromBody
import java.io.IOException
import javax.inject.Inject

sealed class WeeklyTotalState {
    object Loading : WeeklyTotalState()
    data class Success(val weeklyTotal: Int) : WeeklyTotalState()
    data class Failure(val message: String) : WeeklyTotalState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val tokenDataStore: TokenDataStore) : ViewModel() {
    private val _weeklyTotalState = MutableStateFlow<WeeklyTotalState>(WeeklyTotalState.Loading)
    val weeklyTotalState = _weeklyTotalState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = RetrofitBuilder.apiService.getWeeklyTotal(tokenDataStore.getToken()!!)
                if (res.isSuccessful) {
                    _weeklyTotalState.value = WeeklyTotalState.Success(res.body()?.total!!)
                } else {
                    _weeklyTotalState.value =
                        WeeklyTotalState.Failure(getErrMessageFromBody(res.errorBody()!!))
                }
            } catch (e: IOException) {
                _weeklyTotalState.value = WeeklyTotalState.Failure("Internet issue?!")
            } catch (e: Exception) {
                _weeklyTotalState.value =
                    WeeklyTotalState.Failure(e.localizedMessage ?: "Something went wrong!")
            }
        }
    }
}