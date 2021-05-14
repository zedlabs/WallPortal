package tk.zedlabs.wallportal.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

@Composable
fun LoadingBox(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.size(40.dp)
        )
    }
}