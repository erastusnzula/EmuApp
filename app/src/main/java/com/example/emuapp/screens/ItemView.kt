package com.example.emuapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.singleItemViewOffline
import com.example.emuapp.components.Review
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.ItemQuantity
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme

@Composable
fun ItemView(navController: NavController, item: ArrayList<Item>) {
    val imageUrl = remember { mutableStateOf(item[0].thumbnail) }
    val itemImagesUrl = remember {mutableStateOf("")}

    Scaffold(
        modifier = Modifier

    ) {innerPadding->
        Column(
            modifier = Modifier
                .background(colorResource(R.color.white))
                .padding(innerPadding)
                .fillMaxSize()
                .padding(top = Sizes.top, end = Sizes.end, bottom = Sizes.bottom, start = Sizes.start)
                .verticalScroll(rememberScrollState())
        ) {
            val width = LocalConfiguration.current.screenWidthDp.dp
            val height = LocalConfiguration.current.screenHeightDp.dp


            Row(
                horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
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
                    .width(width-50.dp)
                    .height(height/3)
                    .align(Alignment.CenterHorizontally),
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
                            .fillMaxHeight()
                    ) {
                        val fvIcon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
                        val tint = remember { mutableStateOf(Color.Black.copy(.3f)) }
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl.value),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                        //ZoomImage(item[0].thumbnail)
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

            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                items(
                    items = item[0].images,
                    itemContent = {
                        itemImagesUrl.value = it
                        OutlinedCard(
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = colorResource(R.color.white)
                            ),
                            modifier = Modifier
                                .padding(5.dp)
                                .width(width/3)
                                .height(height/7),
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
                                        .fillMaxHeight()
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(itemImagesUrl.value),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                imageUrl.value = it
                                            }
                                    )

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
                        .background(colorResource(R.color.white))
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Spacer(Modifier.height(5.dp))
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ){
                        Text(
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            text = item[0].title,
                            fontSize = 18.sp,
                            color = colorResource(R.color.primary),
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ){
                        Text(
                            text = "Category: ",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            text = item[0].category,
                            fontSize = 16.sp,
                            color = colorResource(R.color.primary),
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ){
                            Text(
                                text = "Price: ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$${item[0].price}",
                                fontSize = 18.sp,
                                color = colorResource(R.color.primary)
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(Sizes.spacer),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                        ){
                            for (star in 1 .. item[0].rating.toInt()){
                                Icon(
                                    modifier = Modifier
                                        .size(15.dp),
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = colorResource(R.color.primary)
                                )
                            }

                        }
                    }
                    Spacer(Modifier.height(5.dp))

                    Column (
                       horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Description",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = item[0].description,
                            textAlign = TextAlign.Justify,
                            fontSize = 16.sp,
                            color = Color.Black.copy(.8f),
                        )
                    }

                }

                Review(reviews = item[0].reviews)

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        val itemsArray = InitialValues.cartItems.value
                        if (!itemsArray.contains(item[0])){
                            itemsArray.add(item[0])
                        }
                        navController.navigate(AllScreens.Cart.route)
                    }) {
                    Text(
                        text = "Buy Now",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }



        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview(){
    EmuAppTheme {
        ItemView(
            navController = rememberNavController(),
            item = arrayListOf<Item>(singleItemViewOffline())
        )
    }
}