package ru.mirea.zotovml.simplefragmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    private lateinit var fragment1:Fragment
    private lateinit var fragment2: Fragment
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment1 = BlankFragment()
        fragment2 = BlankFragment2()
    }

    fun onClick(view: View) {
        fragmentManager = supportFragmentManager
        when (view.id){
            R.id.btnFragment1 -> {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment1)
                    .commit()
            }
            R.id.btnFragment2 -> {
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment2)
                    .commit()
            }
        }
    }
}