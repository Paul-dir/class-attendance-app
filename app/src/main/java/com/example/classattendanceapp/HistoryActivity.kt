package com.example.classattendanceapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryApi {
    @GET("getHistory.php")
    fun getHistory(@Query("student_id") studentId: String): Call<List<Map<String, String>>>
}

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.100/attendance_api/") // Replace with your PC's IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         val api = ApiClient.retrofit.create(HistoryApi::class.java)

        val historyText = findViewById<TextView>(R.id.historyText)
        val studentId = intent.getStringExtra("student_id") ?: return

        api.getHistory(studentId).enqueue(object : Callback<List<Map<String, String>>> {
            override fun onResponse(call: Call<List<Map<String, String>>>, response: Response<List<Map<String, String>>>) {
                if (response.isSuccessful) {
                    val history = StringBuilder()
                    response.body()?.forEach { record ->
                        val className = record["class_name"]
                        val date = record["date"]
                        val status = record["status"]
                        history.append("Class: $className, Date: $date, Status: $status\n")
                    }
                    historyText.text = history.toString()
                } else {
                    historyText.text = "Error: Failed to fetch history"
                }
            }

            override fun onFailure(call: Call<List<Map<String, String>>>, t: Throwable) {
                historyText.text = "Error: ${t.message}"
            }
        })
    }
}

