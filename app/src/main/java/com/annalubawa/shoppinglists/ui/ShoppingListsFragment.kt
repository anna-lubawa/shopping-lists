package com.annalubawa.shoppinglists.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentShoppingListsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListsFragment : Fragment() {

    private lateinit var binding: FragmentShoppingListsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListsFragment()
    }
}