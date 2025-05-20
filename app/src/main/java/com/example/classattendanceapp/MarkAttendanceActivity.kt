// MarkAttendanceActivity.kt
package com.example.classattendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MarkAttendanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkAttendanceScreen()
        }
    }
}

@Composable
fun MarkAttendanceScreen() {
    Text("This is the Mark Attendance Screen")
}
