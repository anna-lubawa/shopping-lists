package com.annalubawa.shoppinglists.ui

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.annalubawa.shoppinglists.R
import com.annalubawa.shoppinglists.domain.model.ShoppingList
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("integerTextBinding")
fun loadInteger(view: TextView, number: Int) {
    view.text = "$number"
}

@BindingAdapter("checkIconBinding")
fun loadCheckIcon(view: ImageView, bought: Boolean) {
    if (bought)
        view.setImageResource(R.drawable.ic_check_filled)
    else
        view.setImageResource(R.drawable.ic_check)
}

@BindingAdapter("cardViewBinding")
fun loadCardView(view: CardView, bought: Boolean) {
    if (bought)
        view.setCardBackgroundColor(Color.argb(255, 139, 221, 176))
    else
        view.setCardBackgroundColor(Color.argb(255, 221, 139, 139))
}

@BindingAdapter("cardViewShoppingListBinding")
fun loadCardView(view: CardView, shoppingList: ShoppingList) {
    if (shoppingList.totalItemsCount != 0 && shoppingList.boughtItemsCount == shoppingList.totalItemsCount)
        view.setCardBackgroundColor(Color.argb(255, 139, 221, 176))
    else
        view.setCardBackgroundColor(Color.argb(255, 221, 139, 139))
}

@BindingAdapter("quantityTextBinding")
fun loadInteger(view: TextView, shoppingList: ShoppingList) {
    if(shoppingList.totalItemsCount != 0 && shoppingList.boughtItemsCount == shoppingList.totalItemsCount)
        view.setTextColor(Color.argb(255, 79, 158, 115))
    else
        view.setTextColor(Color.argb(255, 159, 86, 86))

    view.text = "${shoppingList.boughtItemsCount}/${shoppingList.totalItemsCount}"
}

@BindingAdapter("actionButtonVisibilityBinding")
fun loadFloatingActionButton(view: FloatingActionButton, archived: Boolean) {
    if (archived)
        view.visibility = View.INVISIBLE
    else
        view.visibility = View.VISIBLE
}

@BindingAdapter("archiveIconBinding")
fun loadArchiveIcon(view: ImageView, archived: Boolean) {
    if (archived)
        view.setImageResource(R.drawable.ic_unarchive)
    else
        view.setImageResource(R.drawable.ic_archive)
}

@BindingAdapter("archiveTextBinding")
fun loadArchiveText(view: TextView, archived: Boolean) {
    if (archived)
        view.setText(R.string.unarchive)
    else
        view.setText(R.string.archive)
}