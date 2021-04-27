package com.annalubawa.shoppinglists.ui.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.annalubawa.shoppinglists.databinding.FragmentCurrentShoppingListsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentShoppingListsFragment : Fragment() {

    private lateinit var binding: FragmentCurrentShoppingListsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCurrentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentShoppingListsFragment()
    }
}