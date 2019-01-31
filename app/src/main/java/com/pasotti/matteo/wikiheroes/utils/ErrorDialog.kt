package com.pasotti.matteo.wikiheroes.utils

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.Window
import android.widget.TextView
import com.pasotti.matteo.wikiheroes.R
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction


class ErrorDialog : DialogFragment() {

    interface okButtonListener {
        fun okPressed()
    }

    private var listener: okButtonListener? = null

    companion object {

        val TAG = "ErrorDialog"

        val ERROR_MSG = "error_msg"


        fun show(fragmentTransaction: FragmentTransaction, msg: String) {
            ErrorDialog().apply {
                arguments = Bundle().apply {
                    putString(ERROR_MSG, msg)
                }
            }.show(fragmentTransaction, "")
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is okButtonListener) {
            listener = activity
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!.applicationContext)

        val errorMsg = arguments!!.getString(ERROR_MSG)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_error, null)

        val txtMsg = view.findViewById<TextView>(R.id.msgTxt)

        val okButton = view.findViewById<Button>(R.id.btnOk)

        txtMsg.text = errorMsg

        builder.setView(view)

        val dialog = builder.create().apply {
            setCanceledOnTouchOutside(false)
        }


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val v = dialog.window?.decorView

        v?.setBackgroundResource(android.R.color.transparent)

        okButton.setOnClickListener {
            dismiss()
        }

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        return dialog

    }
}