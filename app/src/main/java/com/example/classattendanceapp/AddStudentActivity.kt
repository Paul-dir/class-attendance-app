package com.example.classattendanceapp

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddStudentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val etName = findViewById<EditText>(R.id.etName)
        val etRoll = findViewById<EditText>(R.id.etRoll)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val roll = etRoll.text.toString()

            if (name.isNotEmpty() && roll.isNotEmpty()) {
                // TODO: Save data locally or send to your PHP API
                Toast.makeText(this, "Student added: $name ($roll)", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
