package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(context = requireContext()).apply {
            setContent {
                MaterialTheme() {
                    imageDetailsSetup()
                }
            }
        }

    }
}

@Composable
fun imageDetailsSetup(modifier: Modifier = Modifier) {

//    Scaffold(
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                onClick = { /* handle on click here */ },
//                text = { BasicText(text = "Options") },
//                icon = { Icon(imageVector = Icons.Filled.Add) },
//                //shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
//                modifier = modifier.offset(y = (-50).dp)
//            )
//        }
//    ){
//
//    }
//    ScrollableColumn(modifier = modifier.padding(horizontal = 4.dp)) {
//        loadImage(url = "https://i.redd.it/jprg32wygg061.jpg")
//
//        Spacer(modifier = Modifier.size(30.dp))
//        Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
//            Card(
//                backgroundColor = Color(0xff347484),
//                elevation = 12.dp,
//                modifier = modifier.clickable(onClick = {}).padding(start = 24.dp)
//            ) {
//                Column(modifier = Modifier.padding(12.dp)) {
//                    detailText(text = "text Line 1 - 1")
//                    detailText(text = "text Line 2 - 2")
//                    detailText(text = "text Line 3 - 3")
//                    detailText(text = "text Line 4 - 4")
//                    detailText(text = "text Line 5 - 5")
//                }
//
//            }
////
////            Column{
////                smallButton(text = "Jetpack Compose", onClick = {})
////                Spacer(modifier = Modifier.size(4.dp))
////                smallButton(text = "Jetpack Compose", onClick = {})
////                Spacer(modifier = Modifier.size(4.dp))
////                smallButton(text = "Jetpack Compose", onClick = {})
////                Spacer(modifier = Modifier.size(4.dp))
////                smallButton(text = "Jetpack Compose", onClick = {})
////            }
//        }
//
//        Spacer(modifier = Modifier.size(40.dp))
//    }

}

@Composable
fun smallButton(text: String, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 6.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff347484))
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = Color(0xffBCE4DC)
            )
        )

    }
}

@Composable
fun detailText(text: String) {
    BasicText(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp,
            color = Color(0xffBCE4DC)
        )
    )
}

//@Composable
//fun loadImage(url: String) {
//    Surface(modifier = Modifier.height(460.dp)) {
//        GlideImage(
//            data = url,
//            loading = {
//                Surface(Modifier.fillMaxSize()) {
//                    Pastel.getColorLight()
//                }
//            },
//            contentScale = ContentScale.Crop,
//            requestBuilder = {
//                val options = RequestOptions()
//                options.centerCrop()
//                apply(options)
//            },
//            fadeIn = true
//        )
//    }
//
//}