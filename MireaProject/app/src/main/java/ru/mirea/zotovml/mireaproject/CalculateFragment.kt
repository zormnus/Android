package ru.mirea.zotovml.mireaproject

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calcRes = view.findViewById<TextView>(R.id.textView3)
        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            calcRes.text = calculate(view).toString()
        }
    }

    private fun calculate(view: View): Double {
        val num1 = view.findViewById<EditText>(R.id.number1Field).text.toString().toDouble()
        val operation:String? = view.findViewById<EditText>(R.id.operationField).text.toString()
        val num2 = view.findViewById<EditText>(R.id.number2Field).text.toString().toDouble()

        if (num2 == 0.0 && operation == "/") {
            val toast = Toast.makeText(view.context, "На 0 делить нельзя!", Toast.LENGTH_SHORT)
            toast.show()
            throw Exception("На 0 делить нельзя!")
        }
        return when (operation) {
            "+" -> (num1 + num2).toDouble()
            "-" -> (num1 - num2).toDouble()
            "/" -> (num1 / num2).toDouble()
            "*" -> (num1 * num2).toDouble()
            else -> 0.0
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}