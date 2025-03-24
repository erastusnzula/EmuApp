package com.example.emuapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.ui.theme.EmuAppTheme

@Composable
fun ZoomImage(imageUrl: String) {
    val scale = remember { mutableStateOf(1f) }
    val offSet = remember { mutableStateOf(Offset(0f, 0f)) }
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    scale.value = scale.value.coerceIn(0.5f, 3.0f)
                    offSet.value = if (scale.value == 1f) Offset(0f, 0f) else offSet.value + pan

                }
            }
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value,
                translationX = offSet.value.x,
                translationY = offSet.value.y
            )
    )

}

@Preview(showBackground = true)
@Composable
fun ZoomPreview(){
    EmuAppTheme {
        ZoomImage("")
    }
}