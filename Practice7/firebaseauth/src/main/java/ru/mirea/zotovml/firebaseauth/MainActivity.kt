package ru.mirea.zotovml.firebaseauth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    private lateinit var mStatusTextView:TextView
    private lateinit var mDetailTextView:TextView
    private lateinit var mEmailField:EditText
    private lateinit var mPasswordField:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStatusTextView = findViewById(R.id.status)
        mDetailTextView = findViewById(R.id.detail)
        mEmailField = findViewById(R.id.fieldEmail)
        mPasswordField = findViewById(R.id.fieldPassword)
        val oclBtn: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val i = v?.id
                when (i) {
                    R.id.emailCreateAccountButton -> {
                        createAccount(mEmailField.text.toString(), mPasswordField.text.toString())
                    }
                    R.id.emailSignInButton -> {
                        signIn(mEmailField.text.toString(), mPasswordField.text.toString())
                    }
                    R.id.emailSignOut -> {
                        signOut()
                    }
                }
            }
        }
        findViewById<Button>(R.id.emailCreateAccountButton).setOnClickListener(oclBtn)
        findViewById<Button>(R.id.emailSignInButton).setOnClickListener(oclBtn)
        findViewById<Button>(R.id.emailSignOut).setOnClickListener(oclBtn)

        mAuth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user:FirebaseUser?) {
        if (user != null) {
            mStatusTextView.text = getString(R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified)
            mDetailTextView.text = getString(R.string.firebase_status_fmt, user.uid)
        }
        else {
            mStatusTextView.text = R.string.signed_out.toString()
            mDetailTextView.text = null
        }
    }

    private fun validateForm() : Boolean {
        var valid = true
        val email = mEmailField.text.toString()
        if (TextUtils.isEmpty(email)) {
            mEmailField.error = "Required."
            valid = false
        }
        else {
            mEmailField.error = null
        }

        val password = mPasswordField.text.toString()
        if (TextUtils.isEmpty(password)) {
            mPasswordField.error = "Required."
            valid = false
        }
        else {
            mEmailField.error = null
        }
        return valid
    }

    private fun createAccount(email:String, password:String) {
        Log.d(TAG, "CreateAccount $email")
        if (!validateForm()) {
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn $email")
        if (!validateForm()) return
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user: FirebaseUser? = mAuth.currentUser
                    updateUI(user)
                }
                else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    mStatusTextView.text = R.string.auth_failed.toString()
                }
            }

    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

}