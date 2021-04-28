package com.annalubawa.shoppinglists.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentShoppingListsBinding
import com.annalubawa.shoppinglists.ui.archived.ArchivedShoppingListsFragment
import com.annalubawa.shoppinglists.ui.current.CurrentShoppingListsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListsFragment : Fragment() {

    private lateinit var binding: FragmentShoppingListsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingListsViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return if(position == 0)
                    CurrentShoppingListsFragment.newInstance()
                else
                    ArchivedShoppingListsFragment.newInstance()
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.shoppingListsViewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Shopping lists"
                    tab.setIcon(R.drawable.ic_list)
                }
                1 -> {
                    tab.text = "Archived shopping lists"
                    tab.setIcon(R.drawable.ic_archive)
                }
            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListsFragment()
    }
}