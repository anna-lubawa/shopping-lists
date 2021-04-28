package com.annalubawa.shoppinglists.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.ShoppinglistElementBinding
import com.annalubawa.shoppinglists.domain.model.ShoppingList

class ShoppingListsRecyclerAdapter(
    private val shoppingLists: List<ShoppingList>,
    private val shoppingListListener: ShoppingListListener
) : RecyclerView.Adapter<ShoppingListsRecyclerAdapter.ShoppingListsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShoppingListsViewHolder(
            DataBindingUtil.inflate<ShoppinglistElementBinding>(
                LayoutInflater.from(parent.context),
                R.layout.shoppinglist_element,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ShoppingListsViewHolder, position: Int) {
        holder.binding.list = shoppingLists[position]
        holder.binding.root.setOnClickListener {
            shoppingListListener.onClick(shoppingLists[position])
        }
        holder.binding.root.setOnLongClickListener {
            shoppingListListener.onLongClick(shoppingLists[position])
            true
        }
    }

    override fun getItemCount(): Int  = shoppingLists.size

    inner class ShoppingListsViewHolder(val binding: ShoppinglistElementBinding)
        : RecyclerView.ViewHolder(binding.root)


    interface ShoppingListListener {
        fun onClick(shoppingList: ShoppingList)
        fun onLongClick(shoppingList: ShoppingList)
    }

}