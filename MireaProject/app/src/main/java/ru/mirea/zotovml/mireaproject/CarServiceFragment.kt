package ru.mirea.zotovml.mireaproject

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text
import ru.mirea.zotovml.mireaproject.roomFiles.App
import ru.mirea.zotovml.mireaproject.roomFiles.PersonDBEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CarServiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarServiceFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor:Sensor

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var regNumber:EditText
    private lateinit var spinnerBrands:Spinner
    private lateinit var spinnerTypes: Spinner
    private lateinit var authBtn:Button
    private lateinit var createBtn:Button
    private lateinit var saveBtn:Button
    private lateinit var mainInfoText:TextView
    private val db = App.instance?.database
    private val personDao = db?.PersonDao()
    @SuppressLint("SetTextI18n")
    private val authListener = View.OnClickListener {
        val person = personDao?.getOne(email.text.toString(), password.text.toString())
        if (person != null) {
            mainInfoText.visibility = View.VISIBLE
            val info = personDao?.getInfo(person.id)?.get(0)
            mainInfoText.text = "Марка автомобиля: ${info?.brandOfCar}" +
                    "\nТип кузова: ${info?.typeOfCar}" +
                    "\nРегистрационный номер: ${info?.regNumber}"

        }else {
            Toast.makeText(this.context, "Такого пользователя не существует",
                Toast.LENGTH_SHORT).show()
        }
    }
    private val createListener = View.OnClickListener {
        val newPerson = PersonDBEntity(0,email.text.toString(), password.text.toString()
            ,spinnerBrands.selectedItem.toString(), spinnerTypes.selectedItem.toString()
            , regNumber.text.toString())
        personDao?.insert(newPerson)
        Toast.makeText(this.context, "Аккаунт успешно создан", Toast.LENGTH_SHORT).show()
    }
    private val saveListener = View.OnClickListener {
        val prevUser = personDao?.getOne(email.text.toString(), password.text.toString())
        val changedUser = PersonDBEntity(prevUser?.id, email.text.toString()
            , password.text.toString(), spinnerBrands.selectedItem.toString()
            ,spinnerTypes.selectedItem.toString(), regNumber.text.toString())
        personDao?.update(changedUser)
    }

    private val brands = arrayOf("Toyota", "Mercedes", "Lada", "Mitsubishi")
    private val types = arrayOf("Sedan", "Hatchback", "Universal", "Coupe")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val autoBrandsAdapter = this.context?.let { ArrayAdapter(it,
            android.R.layout.simple_spinner_item,
            brands) }
        val carsTypesAdapter = this.context?.let { ArrayAdapter(it,
            android.R.layout.simple_spinner_item,
            types) }
        carsTypesAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        autoBrandsAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        regNumber = view.findViewById(R.id.regNumber)
        email = view.findViewById(R.id.emailText)
        password = view.findViewById(R.id.passwordText)
        spinnerBrands = view.findViewById(R.id.brandsSpinner)
        spinnerTypes = view.findViewById(R.id.typesSpinner)
        createBtn = view.findViewById(R.id.createBtn)
        authBtn = view.findViewById(R.id.authButton)
        mainInfoText = view.findViewById(R.id.mainInfo)
        saveBtn = view.findViewById(R.id.saveBtn)
        authBtn.setOnClickListener(this.authListener)
        createBtn.setOnClickListener(this.createListener)
        saveBtn.setOnClickListener(this.saveListener)
        spinnerBrands.adapter = autoBrandsAdapter
        spinnerTypes.adapter = carsTypesAdapter


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_service, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}