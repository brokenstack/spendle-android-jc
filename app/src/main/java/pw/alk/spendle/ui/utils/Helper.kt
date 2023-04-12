package pw.alk.spendle.ui.utils

import okhttp3.ResponseBody
import org.json.JSONObject

fun getErrMessageFromBody(errorBody: ResponseBody): String {
    val jsonObj = JSONObject(errorBody.charStream().readText())
    val errMessage = try {
        jsonObj.getString("error")
    } catch (e: Exception) {
        "Something went wrong!"
    }
    return errMessage
}

suspend fun saveToDataStore(key: String, value: String) {

}