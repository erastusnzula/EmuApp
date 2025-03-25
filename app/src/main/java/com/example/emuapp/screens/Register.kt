package com.example.emuapp.screens

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.emuapp.R
import com.example.emuapp.components.NotificationBody
import com.example.emuapp.data.Sizes
import com.example.emuapp.model.AuthModel
import com.example.emuapp.model.CustomerStatus
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Register(navController: NavController, authModel: AuthModel) {
    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirm = remember { mutableStateOf("") }
    val errorMessage = remember{mutableStateOf("")}
    val checked = remember { mutableStateOf(true) }
    val authStatus = authModel.authStatus.observeAsState()
    val context = LocalContext.current
    val notificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationBody = NotificationBody(context, "Signed up successfully", "Sign Up")
    LaunchedEffect(key1 = true) {
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
        }
    }
    LaunchedEffect(authStatus.value) {
        when(authStatus.value){
            is CustomerStatus.AUTHENTICATED ->{
                navController.navigate(AllScreens.Home.route)
                notificationBody.showNotification()
            }
            is CustomerStatus.Error->{
                errorMessage.value = (authStatus.value as CustomerStatus.Error).message
            }
            else->Unit
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordVisible = remember { mutableStateOf(false) }
    val visibleIcon = Icons.Default.Visibility
    val inVisibleIcon = Icons.Default.VisibilityOff
    Scaffold(
        containerColor = colorResource(R.color.white),
        modifier = Modifier


    ) {innerPadding->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = Sizes.top, bottom = Sizes.bottom, end = Sizes.end, start = Sizes.start)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Get started",
                textAlign = TextAlign.Center,
                //fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            if(errorMessage.value != null){
                Spacer(Modifier.height(Sizes.spacer))
                Text(
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    text= errorMessage.value,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            Spacer(Modifier.height(Sizes.spacer))
            TextField(
                label = {
                    Text(
                        text="First Name"
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(R.color.primary),
                    focusedIndicatorColor = colorResource(R.color.primary),
                ),
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = firstName.value,
                onValueChange = {firstName.value = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
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
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(R.color.primary),
                    focusedIndicatorColor = colorResource(R.color.primary),
                ),
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = lastName.value,
                onValueChange = {lastName.value = it},
                placeholder = {
                    Text(
                        text = "Last Name"
                    )
                },
                label = {
                    Text(
                        text="Last Name"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(R.color.primary),
                    focusedIndicatorColor = colorResource(R.color.primary),
                ),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = email.value,
                onValueChange = {email.value = it},
                placeholder = {
                    Text(
                        text = "Email Address"
                    )
                },
                label = {
                    Text(
                        text="Email Address"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            TextField(
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(R.color.primary),
                    focusedIndicatorColor = colorResource(R.color.primary),
                ),
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = password.value,
                onValueChange = {password.value = it},
                placeholder = {
                    Text(
                        text = "Password"
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        text="Password"
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible.value = !passwordVisible.value
                        }
                    ) {
                        Icon(
                            imageVector = if(passwordVisible.value) visibleIcon else inVisibleIcon,
                            contentDescription = null,
                            tint = colorResource(R.color.primary)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            TextField(
                label = {
                    Text(
                        text="Re-Enter Password"
                    )
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    authModel.register(
                        firstName = firstName.value,
                        lastName = lastName.value,
                        email = email.value,
                        password = password.value,
                        confirmPassword = passwordConfirm.value
                    )
                }),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = colorResource(R.color.primary),
                    focusedIndicatorColor = colorResource(R.color.primary),
                ),
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                value = passwordConfirm.value,
                onValueChange = {passwordConfirm.value = it},
                placeholder = {
                    Text(
                        text = "Re-Enter Password"
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible.value = !passwordVisible.value
                        }
                    ) {
                        Icon(
                            imageVector = if(passwordVisible.value) visibleIcon else inVisibleIcon,
                            contentDescription = null,
                            tint = colorResource(R.color.primary)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(Sizes.spacer))
            Row {
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {checked.value = it},
                    modifier = Modifier,
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.primary)
                    )
                )
                TextButton(
                    onClick = {}
                ){
                    Text(
                        text = "I accept terms and conditions"
                    )
                }
            }
            Spacer(Modifier.height(Sizes.spacer))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary)
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    authModel.register(
                        firstName = firstName.value,
                        lastName = lastName.value,
                        email = email.value,
                        password = password.value,
                        confirmPassword = passwordConfirm.value
                    )
                }
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(Modifier.height(Sizes.spacer))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                HorizontalDivider(Modifier.width(width/3))
                Text(
                    text = "Sign up With"
                )
                HorizontalDivider(Modifier.width(width/3))
            }
            Spacer(Modifier.height(Sizes.spacer))
            OutlinedButton(
                border = BorderStroke(1.dp, colorResource(R.color.primary)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally),
                onClick = {}
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Icon(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                    Text(
                        text = "Google",
                        color = colorResource(R.color.primary),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(Modifier.height(Sizes.spacer))

            TextButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    navController.navigate(AllScreens.LogIn.route)
                }
            ) {
                Text(
                    text = "Already have an account? Sign in",
                    color = colorResource(R.color.primary)
                )
            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Register(navController = rememberNavController(),
        authModel = AuthModel())
}