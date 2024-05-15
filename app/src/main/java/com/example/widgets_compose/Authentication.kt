package com.example.widgets_compose

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
        .setIsSmartLockEnabled(true)
        .setTosAndPrivacyPolicyUrls("https://www.udl.cat",
            "https://www.eps.udl.cat")
        .build()
}

fun onSignInResult(result: FirebaseAuthUIAuthenticationResult, activity: MainActivity, viewModel: SendMoneyViewModel) {
    val response = result.idpResponse


    if (result.resultCode == RESULT_OK) {
        // Successfully signed in
        val currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(currentUser!!.uid)
        Toast.makeText(activity, "User signed in ${currentUser.email}", Toast.LENGTH_SHORT)
            .show()

        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.exists()) {
                    val user = hashMapOf(
                        "uid" to currentUser.uid,
                        "email" to currentUser.email,
                        "tokens" to 0,
                        "location" to mapOf("latitude" to viewModel.userLatitude, "longitude" to viewModel.userLongitude),
                        "fcmToken" to 0,
                    )

                    userRef.set(user).addOnSuccessListener {
                        Toast.makeText(
                            activity,
                            "User added to Firestore",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            activity,
                            "Error adding user to Firestore: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else {
                    viewModel.getTokens()
                }
            }
        }

    } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
        if (response == null) {
            // User pressed back button
            Toast.makeText(activity, "Signed up cancelled", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
            activity.finish()
            return;
        }
        Log.e(TAG, "Sign-in error: ", response.error);
        Toast.makeText(activity, "Sign-in error", Toast.LENGTH_SHORT).show()
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