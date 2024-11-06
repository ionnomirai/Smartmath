package com.example.smartmath.dialogs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmath.databinding.CardRvDialogHelpBinding

class AdapterDialogHelp(val dataList: List<Pair<String, String>>) :
    RecyclerView.Adapter<AdapterDialogHelp.DialogHelpViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogHelpViewHolder {
        val bindingOuter = CardRvDialogHelpBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogHelpViewHolder(bindingOuter)
    }

    override fun onBindViewHolder(holder: DialogHelpViewHolder, position: Int) {
        holder.setData(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    class DialogHelpViewHolder(val innerBinding: CardRvDialogHelpBinding) :
        RecyclerView.ViewHolder(innerBinding.root) {

        fun setData(element: Pair<String, String>) {
            innerBinding.tvHelpSign.text = element.first
            innerBinding.tvHelpSignDescription.text = element.second
        }
    }
}