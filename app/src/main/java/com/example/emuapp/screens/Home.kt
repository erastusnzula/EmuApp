package com.example.emuapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.apiCall
import com.example.emuapp.api.apiCallByCategory
import com.example.emuapp.api.dataOffline
import com.example.emuapp.components.OutlinedCardView
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme


val allItems = apiCall()
val allItemsByCategory = apiCallByCategory()

@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    SnackbarHost(hostState = SnackbarHostState(), modifier = Modifier, snackbar = {
        InitialValues.snackBarMessage.value
    })

    InitialValues.fetchedItems = allItems

    Scaffold(
        modifier = Modifier
            .padding(top = Sizes.top, start = Sizes.start, end = Sizes.end, bottom = Sizes.bottom),
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        containerColor = Color.White,
        contentColor = Color.Black,
        contentWindowInsets = WindowInsets.systemBars,
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Search()
            Spacer(Modifier.height(Sizes.spacer))
            Text(
                text = InitialValues.error.value,
                color = Color.Red.copy(0.5f),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            OfferDisplay(items = allItems, navController = navController)
            Spacer(Modifier.height(Sizes.spacer))
            if (allItems.size != 0) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Featured Items",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black.copy(.8f)
                    )
                    TextButton(onClick = {
                        navController.navigate(AllScreens.Items.route)
                    }) {
                        Text(
                            text = "See All",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorResource(R.color.primary)
                        )
                    }
                }
            }
            Featured(featuredItems = allItems, navController = navController)
            Spacer(Modifier.height(Sizes.spacer))
            if (allItems.size != 0) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Most Popular Items",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black.copy(.8f)
                    )
                    TextButton(onClick = {
                        navController.navigate(AllScreens.Items.route)
                    }) {
                        Text(
                            text = "See All",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorResource(R.color.primary)
                        )
                    }
                }
            }
            MostPopular(popularItems = allItems, navController = navController)
            Spacer(Modifier.height(Sizes.spacer))
            if (allItems.size != 0) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Electronics",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black.copy(.8f)
                    )
                    TextButton(onClick = {
                        navController.navigate(AllScreens.Items.route)
                    }) {
                        Text(
                            text = "See All",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorResource(R.color.primary)
                        )
                    }
                }
            }
            Electronics(
                electronicsItems = allItemsByCategory,
                navController = navController
            )
//            Button(
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(R.color.primary)
//                ),
//                onClick = {
//                    scope.launch {
//                        snackBarHostState.showSnackbar("Snack Bar")
//                    }
//                }) {
//                Text("Show")
//            }
        }

    }
}


@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .size(Sizes.topLeft)
                    .clip(CircleShape),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = colorResource(R.color.primary),
                    modifier = Modifier
                        .size(Sizes.topLeft)
                        .clip(CircleShape)
                )
            }
            Text(
                text = "Hi,Erastus"
            )
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = colorResource(R.color.primary),
            modifier = Modifier
                .size(Sizes.icon)
        )

    }

}

@Composable
fun HomeBottomBar(modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(Sizes.icon)
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(Sizes.icon)
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(Sizes.icon)
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(modifier: Modifier = Modifier) {
    val searchQuery = remember { mutableStateOf("") }
    val active = remember { mutableStateOf(false) }
    val searchList = listOf("bananas", "beans", "carrots")
    val searchOutPut = searchList.filter { it.contains(searchQuery.value, ignoreCase = true) }
    SearchBar(
        colors = SearchBarDefaults.colors(
            containerColor = colorResource(R.color.primary).copy(.2f)
        ),
        query = searchQuery.value,
        onQueryChange = { },
        onSearch = { },
        active = active.value,
        onActiveChange = { },
        modifier = Modifier
            .fillMaxWidth(),
        //.border(width = 1.dp, color = colorResource(R.color.primary), shape = RoundedCornerShape(10.dp)),
        placeholder = { Text(text = "Search here") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = colorResource(R.color.primary)
            )
        },
        trailingIcon = {
            if (active.value) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = colorResource(R.color.black)
                )
            }
        },
        shape = RoundedCornerShape(Sizes.borderRadius),
        tonalElevation = 0.dp,
        windowInsets = WindowInsets.systemBars,
    ) {
        searchOutPut.forEach {
            Text(
                text = "jhhjh"
            )
        }

    }

}

@Composable
fun OfferDisplay(
    items: ArrayList<Item>,
    navController: NavController
) {
    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer)
    ) {
        items(
            items = items.takeLast(5),
            itemContent = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(width - 20.dp)
                        .clip(RoundedCornerShape(Sizes.borderRadius))
                        .height(height / 6)
                        .background(colorResource(R.color.primary))


                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(start = Sizes.spacer)
                            .fillMaxHeight()
                            .clickable {
                                val itemParam = ArrayList<Item>()
                                itemParam.add(it)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "item",
                                    itemParam
                                )
                                navController.navigate(AllScreens.ItemView.route)

                            }
                    ) {
                        Text(
                            text = "Item on Offer",
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${it.discountPercentage}% Off",
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = it.title,
                            color = colorResource(R.color.white),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Image(
                        painter = rememberAsyncImagePainter(it.thumbnail),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(Sizes.borderRadius))
                            .clickable {
                                val itemParam = ArrayList<Item>()
                                itemParam.add(it)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "item",
                                    itemParam
                                )
                                navController.navigate(AllScreens.ItemView.route)

                            },
                        contentScale = ContentScale.FillHeight
                    )
                }

            }
        )
    }
    Spacer(Modifier.height(Sizes.spacer))
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items.takeLast(5).forEach {
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(10.dp)
            )
        }

    }
}

@Composable
fun Featured(
    featuredItems: ArrayList<Item>,
    navController: NavController
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer)
    ) {
        items(
            items = featuredItems.takeLast(30), itemContent = {
                OutlinedCardView(item = it, navController = navController)
            }
        )
    }

}

@Composable
fun MostPopular(
    popularItems: ArrayList<Item>,
    navController: NavController
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer)
    ) {
        items(
            items = popularItems.take(30), itemContent = {
                OutlinedCardView(item = it, navController = navController)
            }
        )
    }

}

@Composable
fun Electronics(
    electronicsItems: ArrayList<Item>,
    navController: NavController
) {


    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Sizes.spacer)
    ) {
        items(
            items = electronicsItems, itemContent = {
                OutlinedCardView(item = it, navController = navController)

            }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    EmuAppTheme {
        Home(navController = rememberNavController())
    }
}