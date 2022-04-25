package com.mirea.zotovml.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyProgressDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.white)
        return progressDialog
    }
}