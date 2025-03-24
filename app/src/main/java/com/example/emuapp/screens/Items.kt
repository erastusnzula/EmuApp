package com.example.emuapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.dataOffline
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme

@Composable
fun Items(navController: NavController) {
    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    val allItems = InitialValues.fetchedItems
    Scaffold(
        modifier=Modifier
            .padding(top=Sizes.top, start = Sizes.start, end = Sizes.end, bottom = Sizes.bottom)
    ) {innerPadding->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(width/4),
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier
                    .fillMaxWidth()

            ) {
                IconButton(onClick = {
                    navController.navigate(route = AllScreens.Home.route)
                }){
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = colorResource(R.color.primary)
                    )
                }

                Text(
                    text = "All Items",
                   fontSize = 20.sp,
                    color = colorResource(R.color.primary)
                )
            }
            Spacer(Modifier.height(Sizes.spacer))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(width/2 - 50.dp),
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = allItems,
                    itemContent = {item->
                        OutlinedCard(
                            onClick = {
                                val itemParam = ArrayList<Item>()
                                itemParam.add(item)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "item",
                                    itemParam
                                )
                                navController.navigate(AllScreens.ItemView.route)
                            },
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = colorResource(R.color.primary).copy(.1f)
                            ),
                            modifier = Modifier
                                .width(width/2-50.dp)
                                .height(height/6+ 10.dp),
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
                                val fvIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
                                val tint = remember { mutableStateOf(Color.Black.copy(.3f)) }
                                Box(
                                    modifier=Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(.7f)
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
                                        onClick = {
                                            val itemParam = ArrayList<Item>()
                                            itemParam.add(item)
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "item",
                                                itemParam
                                            )
                                            navController.navigate(AllScreens.ItemView.route)
                                        }
                                    ) {
                                        Text(
                                            text = "$ ${item.price}"
                                        )
                                    }
                                    TextButton(
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = colorResource(R.color.primary)
                                        ),
                                        onClick = {val itemParam = ArrayList<Item>()
                                            itemParam.add(item)
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "item",
                                                itemParam
                                            )
                                            navController.navigate(AllScreens.ItemView.route)}
                                    ) {
                                        Text(
                                            text = "View"
                                        )
                                    }
                                }
                            }
                        }

                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsPreview(){
    EmuAppTheme {
        Items(navController = rememberNavController())
    }
}