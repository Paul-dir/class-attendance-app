package com.example.classattendanceapp
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AttendanceApi {
    @POST("markAttendance.php")
    fun markAttendance(@Body data: AttendanceData): Call<Map<String, String>>

    @GET("verifyQrCode.php")
    fun verifyQrCode(
        @Query("class_name") className: String,
        @Query("date") date: String,
        @Query("code") code: String
    ): Call<Map<String, Any>>
}

data class AttendanceData(
    val student_id: String,
    val class_name: String,
    val date: String,
    val status: String = "Present"
)

class MainActivity : AppCompatActivity() {
    private lateinit var api: AttendanceApi
    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val studentId = findViewById<EditText>(R.id.studentIdInput).text.toString()
            if (studentId.isEmpty()) {
                Toast.makeText(this, "Please enter Student ID", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }
            val qrData = result.contents.split("|")
            if (qrData.size == 3) {
                val className = qrData[0]
                val date = qrData[1]
                val code = qrData[2]
                verifyAndMarkAttendance(studentId, className, date, code)
            } else {
                Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = ApiClient.retrofit.create(AttendanceApi::class.java)

        val scanButton = findViewById<Button>(R.id.scanButton)
        val loginButton = findViewById<Button>(R.id.loginButton)

        scanButton.setOnClickListener {
            val options = ScanOptions()
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            options.setPrompt("Scan the teacher's QR code")
            scanLauncher.launch(options)
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun verifyAndMarkAttendance(studentId: String, className: String, date: String, code: String) {
        api.verifyQrCode(className, date, code).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful && response.body()?.get("valid") == true) {
                    val data = AttendanceData(studentId, className, date)
                    api.markAttendance(data).enqueue(object : Callback<Map<String, String>> {
                        override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                            if (response.isSuccessful && response.body()?.get("message") == "Attendance marked") {
                                Toast.makeText(this@MainActivity, "Attendance Marked for $className", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity, "Error: ${response.body()?.get("error")}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.body()?.get("error")}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}