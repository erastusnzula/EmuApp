package com.example.emuapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.emuapp.R
import com.example.emuapp.api.dataOffline
import com.example.emuapp.api.singleItemViewOffline
import com.example.emuapp.components.getSubTotal
import com.example.emuapp.components.getTotal
import com.example.emuapp.components.getTotalDiscount
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme


@Composable
fun Cart(navController: NavController) {
    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp

    Scaffold(
        containerColor = colorResource(R.color.white)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    top = Sizes.top,
                    bottom = Sizes.bottom,
                    end = Sizes.end,
                    start = Sizes.start
                )
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                IconButton(onClick = {
                    navController.navigate(AllScreens.Home.route)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = colorResource(R.color.primary)
                    )
                }
                Text(
                    text = "Cart",
                    fontSize = 20.sp,
                    color = colorResource(R.color.primary)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = colorResource(R.color.primary)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Sizes.spacer),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height - 400.dp)

            ) {

                items(items = dataOffline(),//,
                    itemContent = {item->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height / 6)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorResource(R.color.primary).copy(.1f))

                        ) {
                            Row(
                                modifier = Modifier
                                    .width(width / 2)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(item.thumbnail),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(100.dp)
                                        .fillMaxHeight()
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 8.dp,
                                                bottomStart = 8.dp
                                            )
                                        )
                                )
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(start = 8.dp)
                                ) {
                                    Text(
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        text = item.title,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Text(
                                        text = item.category,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis

                                        )
                                    Text(
                                        text = "$${item.price}",
                                        color = colorResource(R.color.primary),
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                            }
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                IconButton(onClick = {
                                    InitialValues.cartItems.value.remove(item)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteForever,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    val quantity = remember { mutableIntStateOf(1) }
                                    IconButton(onClick = {
                                        if (quantity.intValue != 1) {
                                            quantity.intValue -= 1
                                        }else{
                                            InitialValues.cartItems.value.remove(item)
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.RemoveCircleOutline,
                                            contentDescription = null,
                                            tint = colorResource(R.color.primary)
                                        )
                                    }
                                    TextField(
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        modifier = Modifier
                                            .size(50.dp),
                                        value = quantity.intValue.toString(),
                                        onValueChange = { quantity.intValue = it.toInt()}
                                    )
                                    IconButton(onClick = {
                                        quantity.intValue +=1

                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.AddCircleOutline,
                                            contentDescription = null,
                                            tint = colorResource(R.color.primary)
                                        )
                                    }
                                }
                            }
                        }
                    })

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 14.dp)
                    .background(colorResource(R.color.primary).copy(.1f))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Cart Summary",
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Number of Items"
                    )
                    Text(
                        text = InitialValues.cartItems.value.size.toString(),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Sub total"
                    )
                    Text(
                        text = getSubTotal(InitialValues.cartItems.value, 1).toString(),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Discount"
                    )
                    Text(
                        text = getTotalDiscount(InitialValues.cartItems.value).toString()
                        ,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Tax"
                    )
                    Text(
                        text = "0",
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(5.dp))
                HorizontalDivider()
                Spacer(Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Total"
                    )
                    Text(
                        text = getTotal(InitialValues.cartItems.value, 1).toString(),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(5.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate(AllScreens.Checkout.route)
                    }
                ) {
                    Text(
                        text = "Checkout"
                    )
                }

            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    EmuAppTheme {
        Cart(
            navController = rememberNavController(),
        )
    }
}