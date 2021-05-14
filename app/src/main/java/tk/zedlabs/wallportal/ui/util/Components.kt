package tk.zedlabs.wallportal.ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.theme.fontFamilyBungee
import tk.zedlabs.wallportal.theme.titleStyle

@Composable
fun TopBar(){
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Wallportal.",
            fontSize = 32.sp,
            color = colorResource(R.color.titleColor),
            style = titleStyle,
            fontFamily = fontFamilyBungee,
        )
    }
}