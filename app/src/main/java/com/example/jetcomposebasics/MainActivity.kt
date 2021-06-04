package com.example.jetcomposebasics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.example.jetcomposebasics.ui.theme.JetComposeBasicsTheme
import com.google.accompanist.coil.rememberCoilPainter
import org.w3c.dom.NameList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

// App Start
@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetComposeBasicsTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

//listOf("Jet", "Compose", "Thunder")
@Composable
fun MyScreenContent(
    names: List<String> = List(20) {
        "Hell Compose $it"
    }
) {
    var counterState by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            NamesList(names = names, modifier = Modifier.weight(1f))

//            Counter(
//                count = counterState,
//                updateCount = { newCount ->
//                    counterState = newCount
//                }
//            )
//            if (counterState > 5) {
//                Text(text = "I love to count!")
//            }
        }
    }
}

@Composable
fun NamesList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(items = names) {
            Column {
                Greeting(name = it)
                Divider()
                NetworkImage()
            }
        }
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Row {
        Button(
            onClick = { updateCount(count + 1) }
        ) {
            Text(text = "I've been clicked $count times")
        }
    }

}

@Composable
fun Greeting(name: String) {

    val context = LocalContext.current


    var isSelected by remember {
        mutableStateOf(false)
    }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) Color.Red else Color.Transparent,
//        animationSpec = tween(durationMillis = 4000)
    )

    Card(
        backgroundColor = targetColor,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .clickable {
                    isSelected = !isSelected
                    Toast.makeText(
                            context,
                            "Clicked",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NetworkImage() {
    Card(elevation = Dp(4.0f)) {
        Image(
            painter = rememberCoilPainter(
                request = "https://picsum.photos/300/300",
                fadeIn = true,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
            ),
            contentDescription = "stringResource(R.string.image_content_desc)",
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    My App
    MyApp {
        MyScreenContent()
    }
}