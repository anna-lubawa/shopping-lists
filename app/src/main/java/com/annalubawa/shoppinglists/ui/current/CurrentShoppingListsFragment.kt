package com.annalubawa.shoppinglists.ui.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentCurrentShoppingListsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.annalubawa.shoppinglists.ui.ShoppingListsRecyclerAdapter
import com.annalubawa.shoppinglists.ui.archived.ArchivedShoppingListsFragmentDirections


@AndroidEntryPoint
class CurrentShoppingListsFragment : Fragment(), ShoppingListsRecyclerAdapter.ShoppingListListener {

    private lateinit var binding: FragmentCurrentShoppingListsBinding
    private lateinit var viewModel: CurrentShoppingListsViewModel
    private lateinit var navController : NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCurrentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation(view)
        initViewModel()
        initRecyclerView()
        setOnClickListeners()
        setObservers()

        viewModel.getCurrentShoppingLists()
    }

    private fun initRecyclerView() {
        binding.shoppingListsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun initNavigation(view: View) {
        navController = Navigation.findNavController(requireActivity(), R.id.shoppingListsViewPager)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CurrentShoppingListsViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun setOnClickListeners() {
        binding.addListActionButton.setOnClickListener {
            showAddNewDialog()
        }
    }

    private fun setObservers() {
        viewModel.currentShoppingLists.observe(viewLifecycleOwner, Observer { shoppingLists ->
            shoppingLists.forEach { Log.i("CURRENT ", it.name) }
            binding.shoppingListsRecyclerView.adapter =
                ShoppingListsRecyclerAdapter(viewModel.currentShoppingLists.value!!, this)
        })
    }

    private fun showAddNewDialog()
    {
        val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.new_shopping_list_dialog)

        dialog.findViewById<Button>(R.id.dialogShoppingListAddButton).setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.dialogShoppingListEditText).text.toString()

            if(name.isNotBlank()) {
                viewModel.addShoppingList(name)
            }

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogShoppingListCancelButton).setOnClickListener {
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

    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentShoppingListsFragment()
    }
}