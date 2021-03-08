package com.example.library.features.deletebook.presentation

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.library.R

class DeleteBookDialogFragment : DialogFragment() {
    lateinit var listener : DeleteBookDialogListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_book_message)
                .setPositiveButton(R.string.ok) { dialog, id -> listener.onAcceptBookDeletion()}
                .setNegativeButton(R.string.cancel) { dialog, id -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        listener = activity as DeleteBookDialogListener
    }

    interface DeleteBookDialogListener {
        fun onAcceptBookDeletion()
    }
}