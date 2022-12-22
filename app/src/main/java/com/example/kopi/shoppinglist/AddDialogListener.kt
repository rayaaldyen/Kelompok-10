package com.example.kopi.shoppinglist

import com.example.kopi.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}