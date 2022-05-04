package ru.mirea.zotovml.mireaproject

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import kotlin.random.Random
import kotlin.random.nextInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var name:EditText
    private lateinit var surname:EditText
    private lateinit var preferences: SharedPreferences
    private val KEY = "KEY${Random.nextInt(0 until 10)}"
    private lateinit var btn:FloatingActionButton
    private lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.editName)
        surname = view.findViewById(R.id.editSurname)
        btn = view.findViewById(R.id.floatingActionButton)
        preferences = activity?.getPreferences(MODE_PRIVATE)!!
        dialog = Dialog(this.requireContext())
        dialog.setTitle("Вопрос")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_view)
        val btn1 = dialog.findViewById<Button>(R.id.yesBtn)
        val btn2 = dialog.findViewById<Button>(R.id.noBtn)
        btn.setOnClickListener {
            dialog.show()
        }

        btn1.setOnClickListener {
            SaveData("${name.text} ${surname.text} was here")
            dialog.dismiss()
        }

        btn2.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun SaveData(str:String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(KEY, str)
        editor.apply()
        Toast.makeText(this.context, "${name.text} ${surname.text} has been saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}