package pw.alk.spendle.ui.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumPad(
    modifier: Modifier,
    callback: (text: String) -> Any
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        NumPadRow(
            listOf("7", "8", "9"),
            callback
        )
        NumPadRow(
            listOf("4", "5", "6"),
            callback
        )
        NumPadRow(
            listOf("1", "2", "3"),
            callback
        )
        NumPadRow(
            listOf(".", "0", "⌫"),
            callback
        )
    }
}

@Composable
private fun NumPadRow(
    texts: List<String>,
    callback: (text: String) -> Any
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (i in texts.indices) {
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = BorderStroke(ButtonDefaults.OutlinedBorderSize, Color.Transparent),
                modifier = Modifier
                    .weight(25f)
                    .size(70.dp)
                    .padding(6.dp),
                shape = CircleShape,
                onClick = { callback(texts[i]) }) {
                Text((texts[i]), fontSize = 30.sp)
            }
        }
    }
}