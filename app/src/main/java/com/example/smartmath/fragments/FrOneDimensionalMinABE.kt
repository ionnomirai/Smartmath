package com.example.smartmath.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartmath.R
import com.example.smartmath.databinding.FrOneDimensionalMinAbeBinding

class FrOneDimensionalMinABE : Fragment() {
    private var _binding: FrOneDimensionalMinAbeBinding? = null
    private val binding
        get(): FrOneDimensionalMinAbeBinding {
            return checkNotNull(_binding) {
                "Cannot access binding because it is null. Is the view visible"
            }
        }

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

        // -------------- temp --------------
        binding.tvTitle.text = getString(R.string.dichotomy_name_underlined)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}