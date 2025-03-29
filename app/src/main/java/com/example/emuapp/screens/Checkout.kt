package com.example.emuapp.screens

import android.widget.CheckBox
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.emuapp.components.getSubTotal
import com.example.emuapp.components.getTotal
import com.example.emuapp.components.getTotalDiscount
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme

@Composable
fun Checkout(navController: NavController) {
    val isCheckedPaypal = remember { mutableStateOf(false) }
    val isCheckedMpesa = remember { mutableStateOf(false) }
    val isCheckedStripe = remember { mutableStateOf(false) }
    val context = LocalContext.current
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
                    text = "Checkout",
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = colorResource(R.color.primary)
                        )
                    }
                    Text(
                        text = "Nairobi, Kenya"
                    )

                    TextButton(onClick = {}) {
                        Text(
                            text = "Change",
                            color = colorResource(R.color.primary)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 14.dp)
                    .background(colorResource(R.color.primary).copy(.1f))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Order Summary",
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
                        text = getTotalDiscount(InitialValues.cartItems.value).toString(),
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


            }
            Spacer(Modifier.height(20.dp))

            Column {
                Text(
                    text = "Choose payment method",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        IconButton(onClick = {
                            isCheckedMpesa.value = false
                            isCheckedStripe.value = false
                            isCheckedPaypal.value = true
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.paypal),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }

                        TextButton(onClick = {
                            isCheckedMpesa.value = false
                            isCheckedStripe.value = false
                            isCheckedPaypal.value = true
                        }) {
                            Text(
                                text = "PayPal"
                            )
                        }
                    }

                    Checkbox(
                        checked = isCheckedPaypal.value,
                        onCheckedChange = {
                            isCheckedPaypal.value = it
                            if (isCheckedPaypal.value) {
                                isCheckedMpesa.value = false
                                isCheckedStripe.value = false
                            }
                        },
                        modifier = Modifier,
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.primary)
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        IconButton(onClick = {
                            isCheckedMpesa.value = true
                            isCheckedStripe.value = false
                            isCheckedPaypal.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Default.Inbox,
                                tint = colorResource(R.color.primary),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }

                        TextButton(onClick = {
                            isCheckedMpesa.value = true
                            isCheckedStripe.value = false
                            isCheckedPaypal.value = false
                        }) {
                            Text(
                                text = "M-Pesa"
                            )
                        }
                    }

                    Checkbox(
                        checked = isCheckedMpesa.value,
                        onCheckedChange = {
                            isCheckedMpesa.value = it
                            if (isCheckedMpesa.value) {
                                isCheckedPaypal.value = false
                                isCheckedStripe.value = false
                            }
                        },
                        modifier = Modifier,
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.primary)
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        IconButton(onClick = {
                            isCheckedMpesa.value = false
                            isCheckedStripe.value = true
                            isCheckedPaypal.value = false
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.stripe),
                                contentDescription = null,
                                tint = colorResource(R.color.primary),
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }

                        TextButton(onClick = {
                            isCheckedMpesa.value = false
                            isCheckedStripe.value = true
                            isCheckedPaypal.value = false
                        }) {
                            Text(
                                text = "Stripe"
                            )
                        }
                    }

                    Checkbox(
                        checked = isCheckedStripe.value,
                        onCheckedChange = {
                            isCheckedStripe.value = it
                            if (isCheckedStripe.value) {
                                isCheckedMpesa.value = false
                                isCheckedPaypal.value = false
                            }
                        },
                        modifier = Modifier,
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.primary)
                        )
                    )
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary)
                ),
                enabled = isCheckedMpesa.value || isCheckedPaypal.value || isCheckedStripe.value,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    Toast.makeText(context, "Coming soon", Toast.LENGTH_LONG).show()
                }) {
                Text(
                    text = "Checkout"
                )
            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutPreview() {
    EmuAppTheme {
        Checkout(navController = rememberNavController())
    }
}