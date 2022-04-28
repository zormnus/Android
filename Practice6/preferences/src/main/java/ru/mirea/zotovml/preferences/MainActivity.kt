package ru.mirea.zotovml.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var inputText:EditText
    private lateinit var loadedText: TextView
    private lateinit var preferences:SharedPreferences;
    val SAVED_TEXT:String = "saved_text";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputText = findViewById(R.id.enterText)
        loadedText = findViewById(R.id.textView3)
        preferences = getPreferences(MODE_PRIVATE)
    }

    @SuppressLint("CommitPrefEdits", "ShowToast")
    fun onLoadText(view: View) {
        val text = preferences.getString(SAVED_TEXT, "Empty")
        loadedText.text = text
    }
    fun onSaveText(view: View) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(SAVED_TEXT, inputText.text.toString())
        editor.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }
}