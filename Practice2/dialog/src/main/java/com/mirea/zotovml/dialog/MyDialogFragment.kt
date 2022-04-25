package com.mirea.zotovml.dialog

import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.mirea.zotovml.dialog.R
import android.content.DialogInterface
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Здравствуй МИРЭА!")
            .setMessage("Успех близок?")
            .setIcon(R.mipmap.ic_launcher_round)
            .setPositiveButton("Иду дальше") { dialog, id ->
                // Закрываем окно
                onOkClicked()
                dialog.cancel()
            }
            .setNeutralButton(
                "На паузе"
            ) { dialog, id ->
                onNeutralClicked()
                dialog.cancel()
            }
            .setNegativeButton(
                "Нет"
            ) { dialog, id ->
                onCancelClicked()
                dialog.cancel()
            }
        return builder.create()
    }

    fun onOkClicked() {
        Toast.makeText(context, "Вы выбрали кнопку \\\"Иду дальше\\\"!"
            ,Toast.LENGTH_LONG).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(context, "Вы выбрали кнопку \\\"На паузе\\\"!"
            ,Toast.LENGTH_LONG).show()
    }

    fun onCancelClicked() {
        Toast.makeText(context, "Вы выбрали кнопку \\\"Нет\\\"!"
            ,Toast.LENGTH_LONG).show()
    }
}