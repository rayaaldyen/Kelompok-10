package com.example.kopi.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kopi.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ShoppingItem)

    @Query("SELECT * FROM shopping_items WHERE item_name LIKE :name")
    fun findBarang(name: String): List<ShoppingItem>
    @Delete
    fun delete(item: ShoppingItem)

    @Update
   fun updateBarang(item: ShoppingItem)



    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}