package com.example.emuapp.screens
import android.net.Uri
import android.os.Build
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.apiCall
import com.example.emuapp.api.apiCallByCategory
import com.example.emuapp.api.dataOffline
import com.example.emuapp.components.NotificationBody
import com.example.emuapp.components.OutlinedCardView
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes
import com.example.emuapp.model.AuthModel
import com.example.emuapp.model.CustomerStatus
import com.example.emuapp.ui.theme.EmuAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


val allItems = apiCall()
val allItemsByCategory = apiCallByCategory()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(navController: NavController, authModel: AuthModel) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val imageUrl = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        imageUrl.value = uri
    }
    val authStatus = authModel.authStatus.observeAsState()
    LaunchedEffect(authStatus.value) {
        when(authStatus.value){
            is CustomerStatus.UNAUTHENTICATED ->{
                navController.navigate(AllScreens.LogIn.route)
            }
            is CustomerStatus.Error ->{
                InitialValues.error.value = (authStatus.value as CustomerStatus.Error).message
            }
            else -> Unit
        }
    }
    val notificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationBody = NotificationBody(context, "Data fetched successfully", "Data fetch")
    LaunchedEffect(key1 = true) {
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
        }
    }

    InitialValues.fetchedItems = allItems

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier,
                drawerShape = DrawerDefaults.shape,
                drawerContainerColor = Color.White,
                windowInsets = WindowInsets.systemBars
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top=20.dp, bottom = 28.dp)
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ){
                        if (imageUrl.value != null) {
                            InitialValues.topBarIcon.value = imageUrl.value
                            AsyncImage(
                                model = imageUrl.value,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .size(100.dp).clip(CircleShape)

                            )
                        }else{
                            Icon(
                                imageVector =Icons.Filled.AccountCircle ,
                                contentDescription = null,
                                tint = colorResource(R.color.primary),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .size(100.dp).clip(CircleShape)

                            )

                        }
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = null,
                            tint = colorResource(R.color.primary),
                            modifier = Modifier
                                .padding(start = 120.dp, bottom = 80.dp)
                                .clickable {
                                    launcher.launch("image/*")
                                }
                        )
                    }
                    Spacer(Modifier.height(Sizes.spacer))
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Account Details"
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(AllScreens.Profile.route)
                        },
                        modifier = Modifier,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = colorResource(R.color.primary).copy(.4f),
                            unselectedIconColor = colorResource(R.color.primary)
                        ),
                    )
                    Spacer(Modifier.height(Sizes.spacer))
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Settings"
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(AllScreens.Settings.route)
                        },
                        modifier = Modifier,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = colorResource(R.color.primary).copy(.4f),
                            unselectedIconColor = colorResource(R.color.primary)
                        ),
                    )
                    Spacer(Modifier.height(Sizes.spacer))
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "FAQs"
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(AllScreens.FAQ.route)
                        },
                        modifier = Modifier,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = colorResource(R.color.primary).copy(.4f),
                            unselectedIconColor = colorResource(R.color.primary)
                        ),
                    )
                    Spacer(Modifier.height(Sizes.spacer))
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = "Contact"
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(AllScreens.Contact.route)
                        },
                        modifier = Modifier,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.ContactPage,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = colorResource(R.color.primary).copy(.4f),
                            unselectedIconColor = colorResource(R.color.primary)
                        ),
                    )
                    Spacer(Modifier.height(Sizes.spacer))
                    Button(
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primary).copy(.6f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            authModel.logOut()
                            navController.navigate(AllScreens.LogIn.route)

                    }) {
                        Text(
                            text = "Log Out",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
        },
        modifier = Modifier
            .background(colorResource(R.color.white)),
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = Color.Blue.copy(.2f)
    ) {
        Scaffold(
            modifier = Modifier
                .padding(top = Sizes.top,bottom = Sizes.bottom),
            topBar = { HomeTopBar(scope=scope, drawerState = drawerState, navController=navController) },
            bottomBar = { HomeBottomBar(navController=navController) },
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
                    .padding( start = Sizes.start, end = Sizes.end)
                    .verticalScroll(rememberScrollState())
            ) {

                Search()
                Spacer(Modifier.height(Sizes.spacer))
                Text(
                    text = InitialValues.error.value,
                    color = colorResource(R.color.primary),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(Sizes.spacer))
                OfferDisplay(items = allItems, navController = navController)
                Spacer(Modifier.height(Sizes.spacer))
                if (allItems.size != 0) {
                    //notificationBody.showNotification()
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
}


@Composable
fun HomeTopBar(scope:CoroutineScope, drawerState: DrawerState, navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
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
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            ) {
                if (InitialValues.topBarIcon.value != null){
                    AsyncImage(
                        model = InitialValues.topBarIcon.value,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(Sizes.topLeft).clip(CircleShape)

                    )

                }else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = colorResource(R.color.primary),
                        modifier = Modifier
                            .size(Sizes.topLeft)
                            .clip(CircleShape)
                    )
                }
            }
            Text(
                text = "Hi, ${currentUser?.displayName}"
            )
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = colorResource(R.color.primary),
            modifier = Modifier
                .size(Sizes.icon)
                .clickable {
                    navController.navigate(AllScreens.Notifications.route)
                }
        )

    }

}

@Composable
fun HomeBottomBar(navController: NavController) {

    Row(
        modifier = Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.navigate(AllScreens.Home.route)
        }) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(Sizes.icon)
            )
        }
        IconButton(onClick = {
            navController.navigate(AllScreens.Settings.route)
        }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = colorResource(R.color.primary),
                modifier = Modifier
                    .size(Sizes.icon)
            )
        }
        IconButton(onClick = {
            navController.navigate(AllScreens.Profile.route)
        }) {
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
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    val active = remember { mutableStateOf(false
    ) }
    val searchList = listOf("bananas", "beans", "carrots")
    val searchOutPut = searchList.filter { it.contains(searchQuery.value, ignoreCase = true) }
    SearchBar(
        colors = SearchBarDefaults.colors(
            containerColor = colorResource(R.color.primary).copy(.1f)
        ),
        query = searchQuery.value,
        onQueryChange = {},
        onSearch = { },
        active = active.value,
        onActiveChange = {},
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
                text = it
            )
        }
        //Toast.makeText(context, "Searching items", Toast.LENGTH_LONG).show()

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
                            text = it.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${it.discountPercentage}% Off",
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "${it.stock} left",
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
        Home(navController = rememberNavController(), AuthModel())
    }
}