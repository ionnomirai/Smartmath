package com.example.smartmath.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.transaction
import androidx.lifecycle.lifecycleScope
import com.example.smartmath.R
import com.example.smartmath.databinding.FrHelloScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FrHelloScreen: Fragment() {

    private val myTag = "FrHelloScreenTag"

    private var _binding: FrHelloScreenBinding? = null
    val binding
        get(): FrHelloScreenBinding{
            return checkNotNull(_binding){
                "Cannot access binding because it is null. Is the view visible"
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrHelloScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000L)
            parentFragmentManager.commit {
                replace(R.id.fcvMain, FrMainScreen())
                setReorderingAllowed(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(myTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(myTag, "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(myTag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(myTag, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(myTag, "onDetach")
    }
}