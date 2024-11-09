package com.example.smartmath.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmath.R
import com.example.smartmath.databinding.FrDetailedSolutionMinAbeBinding
import com.example.smartmath.fragments.adapters.AdapterDetailedSolutionMinABE
import com.example.smartmath.methods.dataclasses.StateMinABE
import com.example.smartmath.utils.Directions
import com.example.smartmath.utils.MethodNames
import com.example.smartmath.utils.getTagFragment
import com.example.smartmath.utils.getUnderlinedText

class FrOneDimMinABEDetails(
    val data: List<StateMinABE>,
    val methodName: MethodNames,
): Fragment() {

    private var _binding: FrDetailedSolutionMinAbeBinding? = null
    private val binding
        get(): FrDetailedSolutionMinAbeBinding{
            return checkNotNull(_binding){
                "Cannot access binding because it is null. Is the view visible"
            }
        }

    private lateinit var adapterMy: AdapterDetailedSolutionMinABE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrDetailedSolutionMinAbeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Set the title, depending on which method was chosen. */
        binding.apply {
            tvTitleTop.text = when(methodName){
                MethodNames.Dichotomy -> getUnderlinedText(getString(R.string.dichotomy_detailed))
                MethodNames.GoldenSection -> getUnderlinedText(getString(R.string.golden_section_detailed))
            }


            adapterMy = AdapterDetailedSolutionMinABE(data, methodName)
            rvDetailedSolution.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterMy
            }

            /* Return to the main screen, depending on which method was chosen. */
            ibHome.setOnClickListener {
                when(methodName){
                    MethodNames.Dichotomy -> parentFragmentManager.popBackStack(
                        getTagFragment(Directions.FromMainToDichotomy), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    MethodNames.GoldenSection -> parentFragmentManager.popBackStack(
                        getTagFragment(Directions.FromMainToGoldenSection), FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}