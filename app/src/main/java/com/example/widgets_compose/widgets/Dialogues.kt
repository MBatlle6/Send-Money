package com.example.widgets_compose.widgets

import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.widgets_compose.R
import com.example.widgets_compose.WidgetsViewModel

@Composable
fun ClosingDialogue(viewModel: WidgetsViewModel, activity: ComponentActivity){
    if(viewModel.closingDialogue.value == true){
        AlertDialog(
            onDismissRequest = { viewModel.hideClosingDialogue() },
            confirmButton = {
                TextButton(onClick = {activity.finish()}) {
                    Text(text = activity.getString(R.string.closing_app_title))
                }
            },
            dismissButton = {
                TextButton(onClick = {viewModel.hideClosingDialogue()}) {
                    Text(text = activity.getString(R.string.remain))
                }
            },
            title = { Text(text = activity.getString(R.string.closing_app_title)) },
            text = { Text(text = activity.getString(R.string.closing_app)) },

            )
    }
}