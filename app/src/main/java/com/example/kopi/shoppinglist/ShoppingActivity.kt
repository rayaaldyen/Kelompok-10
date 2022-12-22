package com.example.kopi.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kopi.R
import com.example.kopi.base.BaseActivity
import com.example.kopi.data.db.ShoppingDatabase
import com.example.kopi.data.db.entities.ShoppingItem
import com.example.kopi.databinding.ActivityShoppingBinding
import com.example.kopi.other.ShoppingItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_shopping.*

private const val TAG = "ShoppingActivity"

@AndroidEntryPoint
class ShoppingActivity : BaseActivity<ActivityShoppingBinding>() {


    private val viewModel: ShoppingViewModel by viewModels()

    override val layoutRes: Int
        get() = R.layout.activity_shopping

    override fun onCreated(instance: Bundle?) {

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()

            if (it.isEmpty()) {
                placeholder.text = "Stock Barang Kosong"
            }

        })

        fab.setOnClickListener {
            AddShoppingItemDialog(
                this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                        placeholder.text = ""

                    }
                }).show()
        }

        viewModel.getAllShoppingItems().observe(this, Observer {
            if (it.isEmpty()) {
                placeholder.text = "Stock Barang Kosong"
            }
            // Calculate Subtotal
            totaltxt.text = "Total Harga Barang: Rp. ${it.map { it.quantity * it.price }.sum()}"
            Log.d(TAG, "Total daftar barang: ${it}")
        })
    }

    override fun backPressedAction() {
        onBackPressed()
    }

}
