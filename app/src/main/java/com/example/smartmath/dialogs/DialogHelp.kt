package com.example.smartmath.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmath.R
import com.example.smartmath.databinding.DialogHelpBinding
import com.example.smartmath.dialogs.adapters.AdapterDialogHelp

class DialogHelp: DialogFragment(){
    private lateinit var binding: DialogHelpBinding
    private lateinit var myAdapter: AdapterDialogHelp

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            binding = DialogHelpBinding.inflate(inflater)
            myAdapter = AdapterDialogHelp(getInstructions())

            binding.apply {
                rvHelp.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = myAdapter
                }

                // close the dialog
                ivClose.setOnClickListener {
                    this@DialogHelp.dismiss()
                }
            }

            builder
                .setView(binding.root)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /* get string-array with possible operations information */
    private fun getInstructions(): List<Pair<String, String>>{
        return activity?.let {
            resources.getStringArray(R.array.help_possible_operations).map { entry ->
                val (sign, description) = entry.split(" \n ")
                sign to description
            }
        } ?: listOf()
    }
}