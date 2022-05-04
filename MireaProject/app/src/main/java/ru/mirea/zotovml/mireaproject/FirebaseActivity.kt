package ru.mirea.zotovml.mireaproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseActivity: AppCompatActivity(){
    private lateinit var emailText:EditText
    private lateinit var passwordText:EditText
    private lateinit var mAuth: FirebaseAuth
    companion object {
        private val TAG = MainActivity::class.simpleName
    }
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        emailText = findViewById(R.id.EmailText)
        passwordText = findViewById(R.id.PasswordText)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun validateForm() : Boolean {
        var valid = true
        val email = emailText.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailText.error = "Required."
            valid = false
        }
        else {
            emailText.error = null
        }

        val password = passwordText.text.toString()
        if (TextUtils.isEmpty(password)) {
            passwordText.error = "Required."
            valid = false
        }
        else {
            passwordText.error = null
        }
        return valid
    }

    private fun signIn(email: String, password: String){
        Log.d(TAG, "signIn $email")
        if (!validateForm()) return
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user: FirebaseUser? = mAuth.currentUser
                }
                else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                if (!task.isSuccessful) {
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    fun onAuthClick(view: View) {
        signIn(emailText.text.toString(), passwordText.text.toString())
    }

}