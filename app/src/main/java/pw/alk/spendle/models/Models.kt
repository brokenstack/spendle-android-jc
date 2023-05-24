package pw.alk.spendle.models

data class WeeklyTotalResponse (
    val total: Int
)

data class TransactionBody (
    val title: String?,
    val type: String,
    val value: Int,
    val timestamp: Long,
)

data class GenericResponse (
    val message: String
)