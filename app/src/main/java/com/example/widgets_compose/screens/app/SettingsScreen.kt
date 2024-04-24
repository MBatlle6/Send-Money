package com.example.widgets_compose.screens.app

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.widgets_compose.MainActivity
import com.example.widgets_compose.R
import com.example.widgets_compose.SendMoneyViewModel
import com.example.widgets_compose.SharedPreferencesData
import com.example.widgets_compose.backAction
import com.example.widgets_compose.ui.theme.Turquoise
import com.example.widgets_compose.widgets.DeleteAccountDialogue
import com.example.widgets_compose.widgets.SignOutDialogue
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun SettingsScreen(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
) {
    if (viewModel.signOutDialogue.value!!){
        SignOutDialogue(activity = activity, viewModel = viewModel)
    }
    if (viewModel.deleteAccountDialogue.value!!){
        DeleteAccountDialogue(activity = activity, viewModel = viewModel)
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = activity.getString(R.string.network),
            fontSize = 30.sp,
            )
        Spacer(modifier = Modifier.height(16.dp))
        CheckList(viewModel = viewModel, activity = activity)
        //Uncomment when it's obvius which password "implementetion" implement
        /*
        Spacer(modifier = Modifier.height(48.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(
                text = "Password",
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.width(140.dp))
            Button(
                onClick = {
                    val user = Firebase.auth.currentUser

                    Firebase.auth.sendPasswordResetEmail(user!!.email!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, "Email sent", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "Email sent.")
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(Turquoise)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out")
            }
        }*/
        Spacer(modifier = Modifier.height(48.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(
                text = activity.getString(R.string.signOut),
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.width(140.dp))
            Button(
                onClick = {viewModel.showSignOutDialogue(true)},
                colors = ButtonDefaults.buttonColors(Turquoise)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out")
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(
                text = activity.getString(R.string.deleteAccount),
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.width(50.dp))
            Button(
                onClick = {viewModel.showDeleteAccountDialogue(true)},
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete",)
            }
        }
    }
}


@Composable
fun CheckList(
    viewModel: SendMoneyViewModel,
    activity: MainActivity
){
    Row(
        verticalAlignment = Alignment.CenterVertically // Add this line
    ) {
        Checkbox(
            checked = viewModel.allowAllConnections.value!!,
            onCheckedChange = {
                if (!viewModel.allowAllConnections.value!!){
                    viewModel.setAllowAllConnections(true)
                }
            },
        )
        Text(
            text = activity.getString(R.string.allowAllConnections),
            fontSize = 20.sp,
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically // Add this line
    ) {
        Checkbox(
            checked = !viewModel.allowAllConnections.value!!,
            onCheckedChange = {
                              if (viewModel.allowAllConnections.value!!){
                                  viewModel.setAllowAllConnections(false)
                              }
            },
        )
        Text(
            text = activity.getString(R.string.justWifi),
            fontSize = 20.sp,
        )
    }
}

