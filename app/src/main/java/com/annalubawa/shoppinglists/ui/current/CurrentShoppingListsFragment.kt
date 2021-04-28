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
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentCurrentShoppingListsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView


@AndroidEntryPoint
class CurrentShoppingListsFragment : Fragment() {

    private lateinit var binding: FragmentCurrentShoppingListsBinding
    private lateinit var viewModel: CurrentShoppingListsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentCurrentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setOnClickListeners()
        setObservers()
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
            shoppingLists.forEach { Log.i("INFO ", it.name) }
        })
    }

    private fun showAddNewDialog()
    {
        val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.new_shopping_list_dialog)

        dialog.findViewById<Button>(R.id.dialogShoppingListAddButton).setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.dialogShoppingListEditText).text.toString()
            viewModel.addShoppingList(name)
            viewModel.getCurrentShoppingLists()
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogShoppingListCancelButton).setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentShoppingListsFragment()
    }
}