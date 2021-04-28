package com.annalubawa.shoppinglists.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.databinding.ItemElementBinding
import com.annalubawa.shoppinglists.domain.model.Item

class ItemsRecyclerAdapter(
    private val items: List<Item>,
    private val itemListener: ItemClickListener
) : RecyclerView.Adapter<ItemsRecyclerAdapter.ItemsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemsViewHolder(
            DataBindingUtil.inflate<ItemElementBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_element,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.binding.item = items[position]
        holder.binding.root.setOnLongClickListener {
            itemListener.onLongClick(items[position])
            true
        }
        holder.binding.checkIconView.setOnClickListener {
            itemListener.onCheckIconClick(items[position])
        }
    }

    override fun getItemCount(): Int  = items.size

    inner class ItemsViewHolder(val binding: ItemElementBinding)
        : RecyclerView.ViewHolder(binding.root)


    interface ItemClickListener {
        fun onLongClick(item: Item)
        fun onCheckIconClick(item: Item)
    }
}