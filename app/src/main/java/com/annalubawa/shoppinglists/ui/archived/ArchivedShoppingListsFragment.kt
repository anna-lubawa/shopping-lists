package com.annalubawa.shoppinglists.ui.archived

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentArchivedShoppingListsBinding
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.ui.ShoppingListsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchivedShoppingListsFragment : Fragment(), ShoppingListsRecyclerAdapter.ShoppingListListener {

    private lateinit var binding: FragmentArchivedShoppingListsBinding
    private lateinit var viewModel: ArchivedShoppingListsViewModel
    private lateinit var navController : NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentArchivedShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation(view)
        initViewModel()
        initRecyclerView()
        setObservers()
    }

    private fun initNavigation(view: View) {
        navController = Navigation.findNavController(requireActivity(), R.id.shoppingListsViewPager)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ArchivedShoppingListsViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun initRecyclerView() {
        binding.archivedShoppingListsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setObservers() {
        viewModel.archivedShoppingLists.observe(viewLifecycleOwner, Observer { shoppingLists ->
            binding.archivedShoppingListsRecyclerView.adapter =
                ShoppingListsRecyclerAdapter(viewModel.archivedShoppingLists.value!!, this)
        })
    }

    private fun showDeleteListDialog(shoppingList: ShoppingList) {
        val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.delete_list_dialog)

        dialog.findViewById<Button>(R.id.dialogDeleteListDeleteButton).setOnClickListener {
            viewModel.deleteShoppingList(shoppingList)

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogDeleteListCancelButton).setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    override fun onClick(shoppingList: ShoppingList) {
        val action = ArchivedShoppingListsFragmentDirections
            .actionGlobalItemsListFragment(shoppingList.id, shoppingList.name, shoppingList.archived)
        navController.navigate(action)
    }

    override fun onLongClick(shoppingList: ShoppingList) {
        showDeleteListDialog(shoppingList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArchivedShoppingListsFragment()
    }
}