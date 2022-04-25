package com.mirea.zotovml.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.app.TimePickerDialog
import android.icu.text.DateTimePatternGenerator
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.sql.Time
import java.time.format.DateTimeFormatter
import java.util.*

class MyTimeDialogFragment : DialogFragment() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        val calendar:Calendar = Calendar.getInstance()
        val hours:Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes:Int = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(context, TimePickerDialog.OnTimeSetListener{ _:TimePicker, _:Int, _:Int-> }
            , hours, minutes, android.text.format.DateFormat.is24HourFormat(context))
    }

}