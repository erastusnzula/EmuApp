package com.example.emuapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes

@Composable
fun OutlinedButtons(
    modifier: Modifier=Modifier, item: Item
) {
    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    var fvIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
    val tint = remember { mutableStateOf(Color.Black.copy(.3f)) }

    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = colorResource(R.color.white)
        ),
        modifier = Modifier
            .width(width/2-28.dp)
            .height(height/5),
        shape = RoundedCornerShape(Sizes.borderRadius),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = Sizes.elevation
        ),
        border = BorderStroke(1.dp, Color.Blue.copy(.1f))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier=Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(item.thumbnail),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    onClick = {
                        if (item in InitialValues.favouriteList){
                            InitialValues.favouriteList.remove(item)
                            fvIcon.value = Icons.Default.FavoriteBorder
                            tint.value = Color.Black.copy(.3f)
                        }else{
                            InitialValues.favouriteList.add(item)
                            fvIcon.value = Icons.Default.Favorite
                            tint.value = Color.Red


                        }
                    }) {
                    Icon(
                        imageVector = fvIcon.value,
                        contentDescription = null,
                        tint = tint.value,
                        modifier = Modifier
                    )
                }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colorResource(R.color.primary)
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = "$ ${item.price}"
                    )
                }
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colorResource(R.color.primary)
                    ),
                    onClick = {}
                ) {
                    Text(
                        text = "View"
                    )
                }
            }
        }
    }
}