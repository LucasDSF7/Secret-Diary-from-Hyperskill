package org.hyperskill.secretdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var etPin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        btnLogin = findViewById(R.id.btnLogin)
        etPin = findViewById(R.id.etPin)

        btnLogin.setOnClickListener {
            handleLoginButtonListener()
        }
    }

    private fun handleLoginButtonListener(){
        if (etPin.text.toString() == "1234") {
            etPin.setError(null)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else {
            etPin.setError("Wrong PIN!")
        }
    }

}