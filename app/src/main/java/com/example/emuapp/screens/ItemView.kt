package com.example.emuapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.singleItemViewOffline
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme

@Composable
fun ItemView(navController: NavController, item: ArrayList<Item>) {
    Scaffold(
        modifier = Modifier
            .padding(top = Sizes.top, end = Sizes.end, bottom = Sizes.bottom, start = Sizes.start)
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val width = LocalConfiguration.current.screenWidthDp.dp
            val height = LocalConfiguration.current.screenHeightDp.dp


            Row(
                horizontalArrangement = Arrangement.spacedBy(width/4),
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier
                    .fillMaxWidth()

            ) {
                IconButton(
                    onClick = {
                        navController.navigate(AllScreens.Home.route)
                    }
                ){
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = colorResource(R.color.primary)
                    )
                }

                Text(
                    text = item[0].title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(R.color.primary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Spacer(Modifier.height(Sizes.spacer))
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = colorResource(R.color.white)
                ),
                modifier = Modifier
                    .width(width-20.dp)
                    .height(height/3),
                //shape = RoundedCornerShape(Sizes.borderRadius),
                elevation = CardDefaults.outlinedCardElevation(
                    defaultElevation =0.dp
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
                        val fvIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
                        val tint = remember { mutableStateOf(Color.Black.copy(.3f)) }
                        Image(
                            painter = rememberAsyncImagePainter(item[0].thumbnail),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.TopEnd),
                            onClick = {
                                if (item[0] in InitialValues.favouriteList){
                                    InitialValues.favouriteList.remove(item[0])
                                    fvIcon.value = Icons.Default.FavoriteBorder
                                    tint.value = Color.Black.copy(.3f)
                                }else{
                                    InitialValues.favouriteList.add(item[0])
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
                }
            }
            Spacer(Modifier.height(Sizes.spacer))


            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(
                    items = item[0].images,
                    itemContent = {
                        OutlinedCard(
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = colorResource(R.color.white)
                            ),
                            modifier = Modifier
                                .padding(5.dp)
                                .width(width/2-20.dp)
                                .height(height/5),
                            //shape = RoundedCornerShape(Sizes.borderRadius),
                            elevation = CardDefaults.outlinedCardElevation(
                                defaultElevation = 0.dp
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
                                    val fvIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
                                    val tint = remember { mutableStateOf(Color.Black.copy(.3f)) }
                                    Image(
                                        painter = rememberAsyncImagePainter(item[0].thumbnail),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd),
                                        onClick = {
                                            if (item[0] in InitialValues.favouriteList){
                                                InitialValues.favouriteList.remove(item[0])
                                                fvIcon.value = Icons.Default.FavoriteBorder
                                                tint.value = Color.Black.copy(.3f)
                                            }else{
                                                InitialValues.favouriteList.add(item[0])
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
                            }
                        }

                    }
                )
            }

            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = colorResource(R.color.white)
                ),
                modifier = Modifier
                    .width(width-20.dp)
                    .fillMaxHeight(),
                //shape = RoundedCornerShape(Sizes.borderRadius),
                elevation = CardDefaults.outlinedCardElevation(
                    defaultElevation = 0.dp
                ),
                border = BorderStroke(1.dp, Color.Blue.copy(.1f))
            ) {
                Column(

                    verticalArrangement = Arrangement.spacedBy(Sizes.spacer),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Name: ",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            text = item[0].title,
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(R.color.primary)
                        )
                    }
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Price: ",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "$${item[0].price}",
                            style = MaterialTheme.typography.titleLarge,
                            color = colorResource(R.color.primary)
                        )
                    }
                    Column (
                       horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Description: ",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = item[0].description,
                            textAlign = TextAlign.Justify,
                            //style = MaterialTheme.typography.titleLarge,
                            color = colorResource(R.color.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {}) {
                    Text(
                        text = "Buy Now",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }



        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview(){
//    EmuAppTheme {
//        ItemView(
//            navController = rememberNavController(),
//            item = arrayListOf<Item>(singleItemViewOffline())
//        )
//    }
//}