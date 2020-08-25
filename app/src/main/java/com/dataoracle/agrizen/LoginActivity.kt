package com.dataoracle.agrizen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dataoracle.agrizen.helper.constants


class LoginActivity : AppCompatActivity() {
    private lateinit var editTextMobile: EditText

    var phoneNumber: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextMobile = findViewById(R.id.editTextMobile)

        findViewById<View>(R.id.buttonContinue).setOnClickListener(View.OnClickListener {
            phoneNumber = editTextMobile.getText().toString().trim()
            if (phoneNumber!!.isEmpty() || phoneNumber!!.length < 10) {
                editTextMobile.setError("Enter a valid mobile")
                editTextMobile.requestFocus()
                return@OnClickListener
            }
            val intent = Intent(this@LoginActivity, VerifyPhoneActivity::class.java).apply {
                putExtra("mobile", phoneNumber)
            }
            startActivityForResult(intent, constants.LAUNCH_VERIFY_CODE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == constants.LAUNCH_VERIFY_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                // todo send phone number back to main activity
                setResult(Activity.RESULT_OK);
                finish()
            } else if(resultCode == constants.VERIFICATION_FAILURE) {

            }
        }
    }
}