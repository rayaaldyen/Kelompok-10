package com.example.kopi.data.repositories

import com.example.kopi.data.db.ShoppingDatabase
import com.example.kopi.data.db.entities.ShoppingItem
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val db: ShoppingDatabase
): ShoppingInterfaces {

    override fun upsert(item: ShoppingItem) = db.getShoppingDao().upsert(item)

    override fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item)

    override fun updateBarang(item: ShoppingItem) = db.getShoppingDao().updateBarang(item)



    override fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()
}