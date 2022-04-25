package com.mirea.zotovml.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.location.GnssAntennaInfo
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class MyDateDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calender: Calendar = Calendar.getInstance()
        val year:Int = calender.get(Calendar.YEAR)
        val month:Int = calender.get(Calendar.MONTH)
        val day:Int = calender.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context as Context,android.R.style.Theme_Material_Dialog
            , DatePickerDialog.OnDateSetListener(){_:DatePicker,_:Int,_:Int,_:Int->}
            ,year,month,day)
    }
}