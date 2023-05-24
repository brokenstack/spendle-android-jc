package pw.alk.spendle.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


// variations of data because why not
data class DateTime(
    val formattedDateString: String,
    val date: String,
    val time: String,
    val timeStamp: Long
)

fun getFormattedDateTime(date: Date): DateTime {
    val currentDate = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val today = dateFormat.format(currentDate.time)
    val yesterday = dateFormat.format(currentDate.apply { add(Calendar.DATE, -1) }.time)
    val selectedDate = dateFormat.format(date)
    val formattedDateString = when (selectedDate) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> selectedDate
    }
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val time = timeFormat.format(date)

    return DateTime(
        formattedDateString = formattedDateString,
        date = selectedDate,
        time = time,
        date.time
    )
}