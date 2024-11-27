package com.example.ecchofooddelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecchofooddelivery.databinding.ActivitySignMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignMainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignMainBinding by lazy {
        ActivitySignMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Inicializamos Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.crearcuebutton.setOnClickListener {
            username = binding.name.text.toString().trim()
            email = binding.emailregistro.text.toString().trim()
            password = binding.passwordregistro.text.toString().trim()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Por favor ingrese sus datos", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.tengocuentabutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Cuenta registrada correctamente", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                user?.let {
                    // Guarda datos adicionales en la base de datos
                    val userId = it.uid
                    val userMap = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "password" to password
                    )
                    database.child("users").child(userId).setValue(userMap)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Error al guardar datos del usuario", Toast.LENGTH_SHORT).show()
                                Log.d("Database", "Error al guardar datos del usuario", dbTask.exception)
                            }
                        }
                }
            } else {
                Toast.makeText(this, "La creación de cuenta falló", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }
    }
}
