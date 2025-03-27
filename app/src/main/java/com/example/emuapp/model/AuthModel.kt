package com.example.emuapp.model

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emuapp.screens.AllScreens
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest

class AuthModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authStatus = MutableLiveData<CustomerStatus>()
    val authStatus:LiveData<CustomerStatus> = _authStatus

    init {
        checkStatus()

    }



    private fun checkStatus(){
        if (auth.currentUser != null){
            _authStatus.value = CustomerStatus.AUTHENTICATED
        }else{
            _authStatus.value = CustomerStatus.UNAUTHENTICATED
        }
    }

    fun googleLogin(results:GetCredentialResponse){
        when(val credential = results.credential){
            is CustomCredential -> {
                _authStatus.value = CustomerStatus.IsLoading
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                    try {
                        val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data)
                        val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken, null)

                        auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
                            if (it.isSuccessful){
                                _authStatus.value = CustomerStatus.AUTHENTICATED
                            }else{
                                _authStatus.value = CustomerStatus.ErrorLogin(message = it.exception?.message.toString())
                            }
                        }

                    }catch (e: Exception){
                        _authStatus.value = CustomerStatus.ErrorLogin(
                            message = e.message.toString()
                        )
                    }
                }
            }
            else ->{
                _authStatus.value = CustomerStatus.ErrorLogin(
                    message = "Unexpected type of credential."
                )
            }
        }


    }

    fun login(email: String, password: String){
        if (email.isNotBlank() && password.isNotBlank()){
            _authStatus.value = CustomerStatus.IsLoading
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful){
                    _authStatus.value = CustomerStatus.AUTHENTICATED
                }else{
                    _authStatus.value = CustomerStatus.ErrorLogin(
                        message = it.exception?.message.toString()
                    )
                }
            }
        }else{
            _authStatus.value = CustomerStatus.ErrorLogin(
                message = "Email and Password can't be blank!!"
            )
        }
    }

    fun register(firstName: String, lastName: String,email: String, password: String, confirmPassword: String, isChecked:Boolean){
        if (email.isNotBlank() && password.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank() && isChecked){
            if (password == confirmPassword){
                _authStatus.value = CustomerStatus.IsLoading
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val user = auth.currentUser
                        val updateProfile = userProfileChangeRequest {
                            displayName = "$firstName $lastName"
                        }
                        user!!.updateProfile(updateProfile).addOnCompleteListener {
                            _authStatus.value = CustomerStatus.AUTHENTICATED
                        }

                    }else{
                        _authStatus.value = CustomerStatus.Error(
                            message = it.exception?.message.toString()
                        )
                    }
                }
            }else{
                _authStatus.value = CustomerStatus.Error(
                    message = "Password does not match!!"
                )
            }

        }else{
            _authStatus.value = CustomerStatus.Error(
                message = "All fields must be filled!!"
            )
        }
    }

    fun logOut(){
        auth.signOut()
        _authStatus.value = CustomerStatus.UNAUTHENTICATED
    }
}


sealed class CustomerStatus{
    data object IsLoading: CustomerStatus()
    data object AUTHENTICATED: CustomerStatus()
    data object UNAUTHENTICATED: CustomerStatus()
    data class Error(val message: String):CustomerStatus()
    data class ErrorLogin(val message: String):CustomerStatus()

}