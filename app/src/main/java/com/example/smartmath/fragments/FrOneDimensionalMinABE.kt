package com.example.smartmath.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.smartmath.R
import com.example.smartmath.databinding.FrOneDimensionalMinAbeBinding
import com.example.smartmath.dialogs.DialogHelp
import com.example.smartmath.methods.dichotomy.dataclasses.StateDichotomy
import com.example.smartmath.methods.dichotomy.dichotomy
import com.example.smartmath.utils.MethodNames
import com.example.smartmath.utils.getUnderlinedText
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException

class FrOneDimensionalMinABE(val methodName: MethodNames) : Fragment() {
    private var _binding: FrOneDimensionalMinAbeBinding? = null
    private val binding
        get(): FrOneDimensionalMinAbeBinding {
            return checkNotNull(_binding) {
                "Cannot access binding because it is null. Is the view visible"
            }
        }

    private var solutionList = listOf<StateDichotomy>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrOneDimensionalMinAbeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create a title of this screen
        binding.tvTitle.text = getUnderlinedText(methodName.title)

        binding.apply {
            bFindSolution.setOnClickListener {
                try{
                    val expressionInput = etExpression.text.toString()
                    val a = etBorderA.text.toString().toDouble()
                    val b = etBorderB.text.toString().toDouble()
                    val accuracy = etAccuracy.text.toString().toDouble()

                    // Test the base expression. We are looking for a possible mistake
                    getTestExpression(expressionInput)
                    
                    when(methodName){
                        MethodNames.Dichotomy ->{
                            solutionList = dichotomy(a, b, accuracy, expressionInput)
                            setAndOpenHideData(
                                step = (solutionList.size - 1).toString(),
                                xend = solutionList.last().xEnd.toString(),
                                fXEnd = solutionList.last().fEnd.toString()
                            )
                        }
                        MethodNames.GoldenSection -> ""
                    }
                } catch (e: UnknownFunctionOrVariableException){
                    Toast.makeText(activity, "Wrong input", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException){
                    Toast.makeText(activity, "Empty input", Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(activity, "Error my", Toast.LENGTH_SHORT).show()
                }

            }

            bShowDetailedSolution.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.fcvMain, FrOneDimMinABEDetails(solutionList, methodName))
                    setReorderingAllowed(true)
                    addToBackStack("Move from FrOneDimensionalMinABE to FrOneDimMinABEDetails")
                }
            }

            ivHelp.setOnClickListener {
                DialogHelp().show(parentFragmentManager, "Open help dialog")
            }
        }
    }

    private fun setAndOpenHideData(step: String, xend: String, fXEnd: String){
        binding.apply {
            tvStep.text = getString(R.string.title_step, step)
            tvXEnd.text = getString(R.string.title_x_end, xend)
            tvFEnd.text = getString(R.string.title_f_x_end, fXEnd)
            gHidenSolution.visibility = View.VISIBLE

            // reset bottom restriction for tvSolution
            ((tvSolution.layoutParams) as ConstraintLayout.LayoutParams).bottomToBottom =
                ConstraintLayout.LayoutParams.UNSET
        }
    }

    private fun getTestExpression(expressionInput: String){
        ExpressionBuilder(expressionInput)
            .variables("x")
            .build()
            .setVariable("x", 1.0)
            .evaluate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}