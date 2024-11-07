package com.example.smartmath.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.smartmath.methods.dataclasses.StateMinABE
import com.example.smartmath.methods.dichotomy.dichotomy
import com.example.smartmath.methods.goldenSection.goldenSection
import com.example.smartmath.utils.Directions
import com.example.smartmath.utils.MethodNames
import com.example.smartmath.utils.getTagFragment
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

    /* The place where the solution will be stored. All iterations.*/
    private var solutionList = listOf<StateMinABE>()

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

        // fill and open the hiden data if this solution saved
        if (solutionList.isNotEmpty()){
            setAndOpenHideData(solutionList.last(),solutionList.size-1)
        }

        binding.apply {
            bFindSolution.setOnClickListener {
                try {
                    /* Collecting the necessary data from the screen fields. */
                    val expressionInput = etExpression.text.toString()
                    val a = etBorderA.text.toString().toDouble()
                    val b = etBorderB.text.toString().toDouble()
                    val accuracy = etAccuracy.text.toString().toDouble()

                    // Test the base expression. We are looking for a possible mistake
                    getTestExpression(expressionInput)

                    // solve the expression using the chosen method
                    solutionList = when (methodName) {
                        MethodNames.Dichotomy -> dichotomy(a, b, accuracy, expressionInput)
                        MethodNames.GoldenSection -> goldenSection(a, b, accuracy, expressionInput)
                    }

                    // fill and open the hiden data
                    setAndOpenHideData(solutionList.last(),solutionList.size-1)

                } catch (e: UnknownFunctionOrVariableException) {
                    Toast.makeText(activity, "Wrong input", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(activity, "Empty input", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(activity, "Error my", Toast.LENGTH_SHORT).show()
                }

            }

            /* move to the screen (fragment) with detailed solution (with information about each iteration */
            bShowDetailedSolution.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.fcvMain, FrOneDimMinABEDetails(solutionList, methodName))
                    setReorderingAllowed(true)
                    addToBackStack(getTagFragment(Directions.FromMethodMinABEToDetailed))
                }
            }

            /* call help screen with information about additional math operations */
            ivHelp.setOnClickListener {
                DialogHelp().show(parentFragmentManager, "Open help dialog")
            }
        }
    }

    /* When there is a solution, you need to open the hidden field for it. */
    private fun setAndOpenHideData(el: StateMinABE, step: Int) {
        binding.apply {
            tvStep.text = getString(R.string.title_step, step.toString())
            tvXEnd.text = getString(R.string.title_x_end, el.xEnd.toString())
            tvFEnd.text = getString(R.string.title_f_x_end, el.fEnd.toString())
            gHidenSolution.visibility = View.VISIBLE

            // reset bottom restriction for tvSolution
            ((tvSolution.layoutParams) as ConstraintLayout.LayoutParams).bottomToBottom =
                ConstraintLayout.LayoutParams.UNSET
        }
    }

    /* The point is that if something goes wrong, we will understand it at this stage and tell the user. */
    private fun getTestExpression(expressionInput: String) {
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