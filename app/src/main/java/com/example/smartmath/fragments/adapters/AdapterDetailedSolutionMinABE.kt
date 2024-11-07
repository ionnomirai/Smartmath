package com.example.smartmath.fragments.adapters

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmath.R
import com.example.smartmath.databinding.CardRvDetailedSolutionMinAbeBinding
import com.example.smartmath.methods.dichotomy.dataclasses.StateDichotomy
import com.example.smartmath.utils.MethodNames
import com.example.smartmath.utils.getBoldText

class AdapterDetailedSolutionMinABE(
    val data: List<StateDichotomy>,
    val method: MethodNames
) : RecyclerView.Adapter<AdapterDetailedSolutionMinABE.AdapterDetailedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDetailedViewHolder {
        val bindingOuter = CardRvDetailedSolutionMinAbeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AdapterDetailedViewHolder(bindingOuter)
    }

    override fun onBindViewHolder(holder: AdapterDetailedViewHolder, position: Int) {
        when (method) {
            MethodNames.Dichotomy -> holder.setDataDichotomy(data[position])
            MethodNames.GoldenSection -> holder.setDataGS()
        }
    }

    override fun getItemCount(): Int = data.size

    class AdapterDetailedViewHolder(val innerBinding: CardRvDetailedSolutionMinAbeBinding) :
        RecyclerView.ViewHolder(innerBinding.root) {



        fun setDataDichotomy(el: StateDichotomy) {
            val position = this.adapterPosition.toString() // current position in the data list
            innerBinding.apply {
                tvStep.text = getAdjustedText(R.string.title_step, ":", position)

                tvAStep.text = getAdjustedText(
                    R.string.char_plus_num, "=", "a", position, el.a.toString()
                )

                tvBStep.text = getAdjustedText(
                    R.string.char_plus_num, "=", "b", position, el.b.toString()
                )

                tvMedium.text = getAdjustedText(R.string.x_midpoint, "=", el.middle.toString())

                tvXFirst.text = getAdjustedText(
                    R.string.char_plus_num, "=", "x", "1", el.x1.toString()
                )

                tvXSecond.text = getAdjustedText(
                    R.string.char_plus_num, "=", "x", "2", el.x2.toString()
                )

                tvFFirst.text = getAdjustedText(
                    R.string.char_plus_num, "=", "F", "1", el.f1.toString()
                )

                tvFSecond.text = getAdjustedText(
                    R.string.char_plus_num, "=", "F", "2", el.f2.toString()
                )

                el.fX1LessThanFX2?.let { tvFMatch.text = if(it) "F1 < F2" else "F2 >= F2" }

                tvAStepPlusOne.text = getAdjustedText(
                    R.string.char_plus_one_plus_num, "=", "a", position, el.aPlOne.toString()
                )

                tvBStepPlusOne.text = getAdjustedText(
                    R.string.char_plus_one_plus_num, "=", "b", position, el.bPlOne.toString()
                )

                tvCondition.text = getAdjustedText(
                    R.string.condition_detailed, "=", position, el.condition.toString()
                )

                if (el.xEnd != null){
                    tvXEnd.apply {
                        text = getAdjustedText(R.string.x_end, "=", el.xEnd.toString())
                        visibility = View.VISIBLE
                    }
                    tvFXEnd.apply {
                        text = getAdjustedText(R.string.f_x_end, "=", el.fEnd.toString())
                        visibility = View.VISIBLE
                    }
                    ivDone.apply {
                        setImageDrawable(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_check))
                        setColorFilter(itemView.context.getColor(R.color.juicy_green))
                    }
                }


            }
        }

        fun setDataGS() {}

        private fun getAdjustedText(@StringRes stringId: Int, divider: String, vararg values: String): SpannableString {
            val text = itemView.context.getString(stringId, *values)
            val indexDiv = text.indexOf(divider) + 1
            return getBoldText(text, 0, indexDiv)
        }

    }
}