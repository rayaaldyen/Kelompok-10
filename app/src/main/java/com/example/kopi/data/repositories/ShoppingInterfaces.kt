package com.example.kopi.data.repositories

import androidx.lifecycle.LiveData
import com.example.kopi.data.db.entities.ShoppingItem

/**
 * @Author: Nayeem Shiddiki Abir
 * @Date: 09-Dec-21
 */
interface ShoppingInterfaces {
    fun upsert(item: ShoppingItem)
    fun delete(item: ShoppingItem)
    fun updateBarang(item: ShoppingItem)

    fun getAllShoppingItems() : LiveData<List<ShoppingItem>>
}