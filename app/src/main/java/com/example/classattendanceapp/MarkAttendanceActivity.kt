package com.example.classattendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.classattendanceapp.ui.theme.ClassAttendanceAppTheme

class MarkAttendanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassAttendanceAppTheme {
                MarkAttendanceScreen()
            }
        }
    }
}

@Composable
fun MarkAttendanceScreen() {
    Text(text = "Mark Attendance Screen")
}

@Preview
@Composable
fun MarkAttendanceScreenPreview() {
    ClassAttendanceAppTheme {
        MarkAttendanceScreen()
    }
}