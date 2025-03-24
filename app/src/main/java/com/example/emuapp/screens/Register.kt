package com.example.emuapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.emuapp.R
import com.example.emuapp.data.Sizes

@Composable
fun Register(navController: NavController) {
    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirm = remember { mutableStateOf("") }
    val checked = remember { mutableStateOf(true) }
    Scaffold(
        containerColor = colorResource(R.color.white),
        modifier = Modifier
            .padding(top = Sizes.top, bottom = Sizes.bottom, end = Sizes.end, start = Sizes.start)

    ) {innerPadding->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Get started"
            )
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Red
                ),
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = firstName.value,
                onValueChange = {firstName.value = it},
                placeholder = {
                    Text(
                        text = "First Name"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            TextField(
                value = lastName.value,
                onValueChange = {lastName.value = it},
                placeholder = {
                    Text(
                        text = "Last Name"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = email.value,
                onValueChange = {email.value = it},
                placeholder = {
                    Text(
                        text = "Email"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = password.value,
                onValueChange = {password.value = it},
                placeholder = {
                    Text(
                        text = "Password"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            TextField(
                value = passwordConfirm.value,
                onValueChange = {passwordConfirm.value = it},
                placeholder = {
                    Text(
                        text = "Re-Enter Password"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {checked.value = it},
                    modifier = Modifier,
                )
                TextButton(
                    onClick = {}
                ){
                    Text(
                        text = "I accept terms and conditions"
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(
                    text = "Sign Up"
                )
            }
            Row {
                HorizontalDivider(Modifier.width(width/3))
                Text(
                    text = "Sign up With"
                )
                HorizontalDivider(Modifier.width(width/3))
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally),
                onClick = {}
            ) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "Google"
                    )
                }
            }

            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "Already have an account? Sign in"
                )
            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Register(navController = rememberNavController())
}