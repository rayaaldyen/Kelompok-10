package com.example.kopi

import android.content.Context
import android.util.Log
import com.example.kopi.data.db.entities.ShoppingItem
import com.example.kopi.data.db.ShoppingDatabase

class SearchBarang(private val context: Context) {

    fun search(query: String): List<ShoppingItem> {
        Thread.sleep(2000)
        Log.d("Searching", "Searching for $query")
        return ShoppingDatabase.createDatabase(context).getShoppingDao().findBarang("%$query%")
    }

}