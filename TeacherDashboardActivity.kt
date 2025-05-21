package com.example.classattendanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.classattendanceapp.ui.theme.ClassAttendanceAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeacherDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up Retrofit for API calls
        val retrofit = Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com/") // Replace with your API URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Example: Create an API service (uncomment and update if you have one)
        // val apiService = retrofit.create(YourApiService::class.java)

        setContent {
            ClassAttendanceAppTheme {
                DashboardScreen()
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    Text(text = "Teacher Dashboard")
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    ClassAttendanceAppTheme {
        DashboardScreen()
    }
}