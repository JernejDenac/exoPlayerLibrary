package com.example.smartgasmetering

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class DeleteConfirmationDialogFragment(
    private val onConfirm: () -> Unit // Funkcija, ki se izvede ob potrditvi
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.delete_confirmation_message))
                .setPositiveButton(getString(R.string.delete_button_text)) { dialog, id ->
                    onConfirm()
                }
                .setNegativeButton(getString(R.string.cancel_button_text)) { dialog, id ->
                    dialog.dismiss() // Zapre dialog
                }

            val alertDialog = builder.create()

            //  barve gumbov po prikazu dialoga-> ker se je avtomasko barvaspremenila pri darkMode
            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                positiveButton?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                negativeButton?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            }

            alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}


