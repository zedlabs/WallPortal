package tk.zedlabs.wallportal.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import tk.zedlabs.wallportal.R

val robotoCus = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(
            fonts = listOf(
                Font(R.font.roboto_light, weight = FontWeight.W300),
            )
        ),
        fontWeight = FontWeight.SemiBold
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(
            fonts = listOf(
                Font(R.font.roboto_thin, weight = FontWeight.W300),
            )
        )
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily(
            fonts = listOf(
                Font(R.font.roboto_light_italic, weight = FontWeight.W300)
            )
        ),
        color = Color.Gray
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily(
            fonts = listOf(
                Font(R.font.fugaz_one, weight = FontWeight.W300)
            )
        )
    )
)

val titleStyle = TextStyle(
    shadow = Shadow(
        color = Color(0xFFE0E0E0),
        blurRadius = 40f
    ),
)

val fontFamilyBungee = FontFamily(
    fonts = listOf(
        Font(R.font.bungeeshade_regular, FontWeight.Normal),
    )
)