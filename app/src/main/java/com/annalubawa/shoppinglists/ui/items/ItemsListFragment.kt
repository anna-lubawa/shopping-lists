package com.annalubawa.shoppinglists.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.FragmentItemsListBinding
import com.annalubawa.shoppinglists.domain.model.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsListFragment : Fragment(), ItemsRecyclerAdapter.ItemClickListener {

    private val args: ItemsListFragmentArgs by navArgs()
    private lateinit var binding: FragmentItemsListBinding
    private lateinit var viewModel: ItemsViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                      savedInstanceState: Bundle?): View? {

        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation(view)
        initViewModel()
        initRecyclerView()
        setOnClickListeners()
        initObservers()

        viewModel.getItems()
    }

    private fun initNavigation(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)
        viewModel.initValues(args.id, args.name, args.archived)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        binding.itemsListRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setOnClickListeners() {
        binding.addItemActionButton.setOnClickListener {
            showAddNewDialog()
        }

        binding.archiveListIcon.setOnClickListener {
            viewModel.archiveOrUnarchiveShoppingList()
        }

        binding.archiveTextView.setOnClickListener {
            viewModel.archiveOrUnarchiveShoppingList()
        }
    }

    private fun initObservers() {
        viewModel.items.observe(viewLifecycleOwner, Observer { items ->
            binding.itemsListRecyclerView.adapter =
                    ItemsRecyclerAdapter(viewModel.items.value!!, this)
        })

        viewModel.archived.observe(viewLifecycleOwner, Observer { archived ->
            if(viewModel.shouldShowToast) {
                if (archived)
                    Toast.makeText(context, "Shopping list archived.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Shopping list unarchived.", Toast.LENGTH_SHORT).show()

                viewModel.shouldShowToast = false
            }
        })
    }

    override fun onLongClick(item: Item) {
        if(!viewModel.archived.value!!)
            showDeleteItemDialog(item)
    }

    override fun onCheckIconClick(item: Item) {
        if (item.bought)
            viewModel.undoBuyItem(item)
        else
            viewModel.buyItem(item)
    }

    private fun showAddNewDialog() {
        val dialog = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.new_item_dialog)

        dialog.findViewById<Button>(R.id.dialogItemAddButton).setOnClickListener {
            val name = dialog.findViewById<EditText>(R.id.dialogItemNameEditText).text.toString()
            val quantity = dialog.findViewById<EditText>(R.id.dialogItemQuantityEditText).text.toString()

            if(name.isNotBlank() && quantity.isNotBlank()) {
                viewModel.addItem(name, quantity.toInt())
            }

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogItemCancelButton).setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    private fun showDeleteItemDialog(item: Item) {
        val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.delete_item_dialog)

        dialog.findViewById<Button>(R.id.dialogDeleteItemDeleteButton).setOnClickListener {
            viewModel.deleteItem(item)

            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.dialogDeleteItemCancelButton).setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemsListFragment()
    }
}