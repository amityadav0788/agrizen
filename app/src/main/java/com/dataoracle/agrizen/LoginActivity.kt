package com.dataoracle.agrizen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var editTextMobile: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextMobile = findViewById(R.id.editTextMobile)

        findViewById<View>(R.id.buttonContinue).setOnClickListener(View.OnClickListener {
            val mobile: String = editTextMobile.getText().toString().trim()
            if (mobile.isEmpty() || mobile.length < 10) {
                editTextMobile.setError("Enter a valid mobile")
                editTextMobile.requestFocus()
                return@OnClickListener
            }
            val intent = Intent(this@LoginActivity, VerifyPhoneActivity::class.java).apply {
                putExtra("mobile", mobile)
            }
            startActivity(intent)
        })
    }
}