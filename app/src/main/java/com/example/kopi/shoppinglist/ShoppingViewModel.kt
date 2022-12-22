package com.example.kopi.shoppinglist

import androidx.lifecycle.ViewModel
import com.example.kopi.data.db.entities.ShoppingItem
import com.example.kopi.data.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    fun upsert(item: ShoppingItem) =
        GlobalScope.launch {
            repository.upsert(item)
        }
    fun updateBarang(item: ShoppingItem) =
        GlobalScope.launch {
            repository.updateBarang(item)
        }

    fun delete(item: ShoppingItem) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.getAllShoppingItems()

}
