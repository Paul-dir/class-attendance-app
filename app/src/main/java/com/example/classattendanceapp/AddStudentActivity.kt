package com.example.classattendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.classattendanceapp.ui.theme.ClassAttendanceAppTheme

class AddStudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassAttendanceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AddStudentScreen()
                }
            }
        }
    }
}

@Composable
fun AddStudentScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add Student", style = MaterialTheme.typography.displayLarge)
        var studentName by remember { mutableStateOf("") }
        OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Student Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Save to XAMPP API */ }) {
            Text("Save")
        }
    }
}