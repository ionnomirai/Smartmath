package com.example.smartmath.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.smartmath.R
import com.example.smartmath.databinding.FrMainScreenBinding

class FrMainScreen : Fragment() {
    private var _binding: FrMainScreenBinding? = null
    private val binding
        get(): FrMainScreenBinding {
            return checkNotNull(_binding) {
                "Cannot access binding because it is null. Is the view visible"
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bDichotomy.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.fcvMain, FrOneDimensionalMinABE())
                    setReorderingAllowed(true)
                    addToBackStack("Move from FrMainScreen to FrOneDimensionalMinABE")
                }
            }
            bGoldenSection.setOnClickListener {
                Toast.makeText(context, "Move to the Golden Section screen", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}