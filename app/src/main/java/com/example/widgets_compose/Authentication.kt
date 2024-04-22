package com.example.widgets_compose

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth

private val providers = arrayListOf(
    AuthUI.IdpConfig.GoogleBuilder().build(),
    AuthUI.IdpConfig.EmailBuilder().build()
)

fun getAuthIntent(): Intent {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val providers = if (currentUser == null) providers
    else {
        arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
    }

    return AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .enableAnonymousUsersAutoUpgrade()
        .setLogo(R.drawable.sendmoney_logo)
        .setTheme(R.style.TurquoiseTheme)
        .setTosAndPrivacyPolicyUrls("https://www.udl.cat",
            "https://www.eps.udl.cat")
        .build()
}

fun onSignInResult(result: FirebaseAuthUIAuthenticationResult, activity: MainActivity, viewModel: SendMoneyViewModel) {
    val response = result.idpResponse
    if (result.resultCode == RESULT_OK) {
        // Successfully signed in
        val user = FirebaseAuth.getInstance().currentUser

    } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
        Toast.makeText(activity, "User canceled Auth Flow", Toast.LENGTH_SHORT).show()
    }
}

private fun getCurrentUser() {
    if (isAuthClient()) {
        val user = auth.currentUser
        val userEmail = user?.email
        val userDisplayName = user?.displayName
        val userUID = user?.uid
        //val userPhoto = user?.photoUrl
    }
}

val auth by lazy {
    FirebaseAuth.getInstance()
}

fun isAuthClient(): Boolean {
    return auth.currentUser != null
}