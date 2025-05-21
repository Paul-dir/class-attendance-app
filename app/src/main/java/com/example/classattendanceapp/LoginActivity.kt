package com.example.classattendanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("signup.php")
    fun signup(@Body data: AuthData): Call<Map<String, String>>

    @POST("login.php")
    fun login(@Body data: AuthData): Call<Map<String, String>>
}

data class AuthData(
    val email: String,
    val password: String,
    val student_id: String? = null
)

class LoginActivity : AppCompatActivity() {
    private lateinit var api: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        api = ApiClient.retrofit.create(AuthApi::class.java)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<Button>(R.id.signupButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val data = AuthData(email, password)
                api.login(data).enqueue(object : Callback<Map<String, String>> {
                    override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                        if (response.isSuccessful && response.body()?.get("message") == "Login successful") {
                            val role = response.body()?.get("role")
                            val studentId = response.body()?.get("student_id")
                            if (role == "teacher") {
                                startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
                            } else {
                                val intent = Intent(this@LoginActivity, HistoryActivity::class.java)
                                intent.putExtra("student_id", studentId)
                                startActivity(intent)
                            }
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Error: ${response.body()?.get("error")}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val data = AuthData(email, password, "STU${(100..999).random()}")
                api.signup(data).enqueue(object : Callback<Map<String, String>> {
                    override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                        if (response.isSuccessful && response.body()?.get("message") == "Sign up successful") {
                            Toast.makeText(this@LoginActivity, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@LoginActivity, "Error: ${response.body()?.get("error")}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}